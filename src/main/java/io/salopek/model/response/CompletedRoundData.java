package io.salopek.model.response;

import io.salopek.model.Point;

public class CompletedRoundData {
  private Point origin;
  private Point antipode;
  private Point submission;
  private double distance;

  private CompletedRoundData(){}

  public CompletedRoundData(Point origin, Point antipode, Point submission, double distance) {
    this.origin = origin;
    this.antipode = antipode;
    this.submission = submission;
    this.distance = distance;
  }

  public Point getOrigin() {
    return origin;
  }

  public void setOrigin(Point origin) {
    this.origin = origin;
  }

  public Point getAntipode() {
    return antipode;
  }

  public void setAntipode(Point antipode) {
    this.antipode = antipode;
  }

  public Point getSubmission() {
    return submission;
  }

  public void setSubmission(Point submission) {
    this.submission = submission;
  }

  public double getDistance() {
    return distance;
  }

  public void setDistance(double distance) {
    this.distance = distance;
  }
}
