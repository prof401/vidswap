package net.april1.vidswap.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
    "playlistId",
    "startOffset",
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
  String startOffset;
  String home;
  String away;
  String shotTeam;
}
