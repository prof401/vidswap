package net.april1.vidswap.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.beans.Transient;
import java.time.LocalTime;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "playlistId",
        "period",
        "videoTime",
        "gameTime",
        "x",
        "y",
        "theta",
        "distanceCenter",
        "distanceLine",
        "goal"
})
public class XGData {
    double x;
    double y;
    double theta;
    double distanceCenter;
    double distanceLine;
    boolean goal;
    int playlistId;
    int period;
    LocalTime videoTime;
    LocalTime gameTime;
    String home;
    String away;
    String shotTeam;

    @Transient
    public boolean isConference() {
        return home.equals(home.toUpperCase()) && away.equals(away.toUpperCase());
    }
}
