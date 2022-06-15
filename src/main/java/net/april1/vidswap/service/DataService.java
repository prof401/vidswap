package net.april1.vidswap.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.april1.vidswap.model.VidswapGame;
import net.april1.vidswap.model.XGData;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class DataService {
    private static final Integer ATTRIBUTE_FIELD_LOCATION = 4291;
    private static final Integer ATTRIBUTE_RESULT = 789;
    private static final Integer ATTRIBUTE_SHOT = 1273;

    private static final double X_LEFT_18 = 144.8;
    private static final double X_RIGHT_18 = 885.2;
    private static final double X_YARD = (X_RIGHT_18 - X_LEFT_18) / 44.0;
    private static final double X_CENTER = (X_RIGHT_18 + X_LEFT_18) / 2.0;

    private static final double Y_GOAL_LINE = 887.2428977;
    private static final double Y_TOP_18 = 642.842879;
    private static final double Y_YARD = (Y_TOP_18 - Y_GOAL_LINE) / 18.0;

    private EventService eventService;
    private GameService gameService;

    @Transient
    private Set<String> teamNameSet = new HashSet<>();

    public Flux<XGData> collectXGData() {
        return gameService.getAll().flatMap(game -> collectGameXGData(game));
    }

    private Flux<XGData> collectGameXGData(VidswapGame game) {
        return eventService.getShots(game.getPlaylistId()).mapNotNull(shot -> {
            XGData dataPoint = new XGData();
            dataPoint.setPlaylistId(game.getPlaylistId());
            dataPoint.setStartOffset(shot.getStartOffset());
            dataPoint.setHome(decodeTeam(game.getHomeTeam()));
            dataPoint.setAway(decodeTeam(game.getAwayTeam()));
            boolean location = false;
            boolean result = false;
            //log.info("tagAttributes {}", shot.getTagAttributes());
            for (Map<String, Object> attribute : shot.getTagAttributes()) {
                //log.info("id value {} {} ", attribute.get("id"), attribute.get("id").getClass().toString() );
                Integer id = (Integer) attribute.get("id");
                if (id.equals(ATTRIBUTE_FIELD_LOCATION)) {
                    dataPoint.setX(getX((Map<String, Number>) attribute.get("value")));
                    dataPoint.setY(getY((Map<String, Number>) attribute.get("value")));
                    location = true;
                }
                if (id.equals(ATTRIBUTE_RESULT)) {
                    dataPoint.setGoal("goal".equals(attribute.get("value")));
                    result = true;
                }
                if (id.equals(ATTRIBUTE_SHOT)) {
                    dataPoint.setShotTeam(decodeTeam((String)attribute.get("value")));
                }
            }
            if (!location || !result) return null; //if missing data do not include
            return dataPoint;
        }).sort(Comparator.comparing(XGData::getPlaylistId).thenComparing(XGData::getStartOffset));

    }

    private double getX(Map<String, Number> fieldLocationMap) {
        return ((fieldLocationMap.get("x").doubleValue() - X_CENTER) / X_YARD);
    }

    private double getY(Map<String, Number> fieldLocationMap) {
        return ((fieldLocationMap.get("y").doubleValue() - Y_GOAL_LINE) / Y_YARD);
    }

    private String decodeTeam(String teamName) {
        switch (teamName.toLowerCase(Locale.ROOT)) {
            case "northwest christian":
            case "bushnell":
                return "BUSHNELL";
            case "carroll":
            case "carroll college":
                return "CARROLL";
            case "college of idaho":
            case "the college of idaho":
                return "COLLEGEOFIDAHO";
            case "corban":
                return "CORBAN";
            case "northwest":
            case "northwest university":
                return "NORTHWEST";
            case "eastern oregon":
                return "EASTERNOREGON";
            case "evergreen":
                return "EVERGREEN";
            case "multnomah":
                return "MULTNOMAH";
            case "oit":
                return "OREGONTECH";
            case "great falls":
            case "providence":
                return "PROVIDENCE";
            case "rocky mountain":
                return "ROCKYMONTAIN";
            case "southern oregon":
                return "SOUTHERNOREGON";
            case "warner pacific":
                return "WARNERPACIFIC";
            default:
                if (!teamNameSet.contains(teamName)) {
                    log.info("Missing team {}", teamName);
                    teamNameSet.add(teamName);
                }
                return teamName.toLowerCase(Locale.ROOT);
        }
    }
}
