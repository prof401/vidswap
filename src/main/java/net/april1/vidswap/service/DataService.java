package net.april1.vidswap.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.april1.vidswap.model.Event;
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
  public static final String VALUE_ATTRIBUTE = "value";

  private EventService eventService;
  private GameService gameService;

  @Transient
  private Set<String> teamNameSet;

  public Flux<XGData> collectXGData() {
    return gameService.getAll().flatMap(this::collectGameXGData);
  }

  private Flux<XGData> collectGameXGData(VidswapGame game) {
    return eventService.getShots(game.getPlaylistId()).mapNotNull(shot -> {
      return getXgData(game, shot);
    }).sort(Comparator.comparing(XGData::getPlaylistId).thenComparing(XGData::getStartOffset));

  }

  private XGData getXgData(VidswapGame game, Event shot) {
    XGData dataPoint = getInitialXgData(game, shot);
    boolean location = false;
    boolean result = false;
    log.debug("tagAttributes {}", shot.getTagAttributes());
    for (Map<String, Object> attribute : shot.getTagAttributes()) {
      log.debug("id value {} {}", attribute.get("id"), attribute.get("id").getClass().toString());
      Integer id = (Integer) attribute.get("id");
      if (id.equals(ATTRIBUTE_FIELD_LOCATION)) {
        setFieldLocationData(dataPoint, attribute);
        location = true;
      }
      if (id.equals(ATTRIBUTE_RESULT)) {
        dataPoint.setGoal("goal".equals(attribute.get(VALUE_ATTRIBUTE)));
        result = true;
      }
      if (id.equals(ATTRIBUTE_SHOT)) {
        dataPoint.setShotTeam(decodeTeam((String) attribute.get(VALUE_ATTRIBUTE)));
      }
    }
    if (!location || !result) {
      //did not find all the data, do not return record
      return null;
    }

    return dataPoint;
  }

  private XGData getInitialXgData(VidswapGame game, Event shot) {
    XGData dataPoint = new XGData();
    dataPoint.setPlaylistId(game.getPlaylistId());
    dataPoint.setStartOffset(shot.getStartOffset());
    dataPoint.setHome(decodeTeam(game.getHomeTeam()));
    dataPoint.setAway(decodeTeam(game.getAwayTeam()));
    return dataPoint;
  }

  private void setFieldLocationData(XGData dataPoint, Map<String, Object> attribute) {
    double localX = getX((Map<String, Number>) attribute.get(VALUE_ATTRIBUTE));
    double localY = getY((Map<String, Number>) attribute.get(VALUE_ATTRIBUTE));
    dataPoint.setX(localX + 40.0);
    dataPoint.setY(localY);
    dataPoint.setTheta(calcTheta(localX, localY));
    dataPoint.setDistanceCenter(calcDCenter(localX, localY));
    dataPoint.setDistanceLine(calcDLine(localX, localY));
  }

  private double getX(Map<String, Number> fieldLocationMap) {
    return ((fieldLocationMap.get("x").doubleValue() - X_CENTER) / X_YARD);
  }

  private double getY(Map<String, Number> fieldLocationMap) {
    return ((fieldLocationMap.get("y").doubleValue() - Y_GOAL_LINE) / Y_YARD);
  }

  private double calcTheta(double x, double y) {
    return Math.toDegrees(Math.atan((8 * y) / ((x * x) + (y * y) - 16)));
  }

  private double calcDCenter(double x, double y) {
    return Math.sqrt((x * x) + (y * y));
  }

  private double calcDLine(double x, double y) {
    double absX = Math.abs(x);
    double dLine = y;
    if (absX > 4) {
      dLine = Math.sqrt(((x - 4) * (x - 4)) + (y * y));
    }
    return dLine;
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
