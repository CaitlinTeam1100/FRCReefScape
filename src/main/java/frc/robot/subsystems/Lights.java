// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.testingdashboard.SubsystemBase;

public class Lights extends SubsystemBase {
  public static Lights m_lights;

  private AddressableLED m_LED;
  private AddressableLEDBuffer m_LEDBuffer;

  private int m_rainbowFirstPixelHue;
  private int m_firstPixelValue;
  private boolean m_blinkState;
  private Timer m_timer;

  /** Creates a new lights. */
  public Lights() {
    super("Lights");

    m_LED = new AddressableLED(RobotMap.L_LEDS);

    m_LEDBuffer = new AddressableLEDBuffer(Constants.LightsConstants.LED_LENGTH);
    m_LED.setLength(Constants.LightsConstants.LED_LENGTH);

    setData();
    m_LED.start();

    m_rainbowFirstPixelHue = 0;
    m_firstPixelValue = 0;
    m_blinkState = true;
    
    m_timer = new Timer();
    m_timer.start();
    m_timer.reset();
  }

  public static Lights getInstance() {
    if (m_lights == null) {
      m_lights = new Lights();
    }
    return m_lights;
  }

  public void setData() {
    m_LED.setData(m_LEDBuffer);
  }

  public void enableLights(int hue) {
    for (var i = 0; i < Constants.LightsConstants.LED_LENGTH; i++) {
      m_LEDBuffer.setHSV(i, hue, 255, 122);
    }

    setData();
  }

  public void makeRainbow() {
    // For every pixel
    for (var i = 0; i < Constants.LightsConstants.LED_LENGTH; i++) {
      // Calculate the hue - hue is easier for rainbows because the color
      // shape is a circle so only one value needs to process
      final var hue = (m_rainbowFirstPixelHue + (i * 180 / Constants.LightsConstants.LED_LENGTH)) % 180;
      // Set the value
      m_LEDBuffer.setHSV(i, hue, 255, 128);
    }
    // Increase by to make the rainbow "move"
    m_rainbowFirstPixelHue += 3;
    // Check bounds
    m_rainbowFirstPixelHue %= 180;
  }

  public void cool() {
    for (var i = 0; i < Constants.LightsConstants.LED_LENGTH; i++) {
      final var hue = (m_rainbowFirstPixelHue + (i * 130 / Constants.LightsConstants.LED_LENGTH)) % 180;
      m_LEDBuffer.setHSV(i, hue, 255, 128);
    }
    m_rainbowFirstPixelHue += 2;
    m_rainbowFirstPixelHue %= 90;
  }

  public void warm() {
    // For every pixel
    for (var i = 0; i < m_LEDBuffer.getLength(); i++) {
      // Calculate the hue - hue is easier for rainbows because the color
      // shape is a circle so only one value needs to precess
      final var hue = 170 + (m_rainbowFirstPixelHue + (i * 120 / m_LEDBuffer.getLength())) % 40;
      // Set the value
      m_LEDBuffer.setHSV(i, hue, 255, 128);
    }
    // Increase by to make the rainbow "move"
    m_rainbowFirstPixelHue += 2;
    // Check bounds
    m_rainbowFirstPixelHue %= 60;
  }

  public void moveLights(int hue) {
    for (var i = 0; i < Constants.LightsConstants.LED_LENGTH; i++) {
      final var value = (m_firstPixelValue + (i * 255 / Constants.LightsConstants.LED_LENGTH)) % 255;
      m_LEDBuffer.setHSV(i, hue, 255, value);
    }
    
    // what "moves" the program
    m_firstPixelValue += 10;
    m_firstPixelValue %= 255;
  }

  public void blinkLights(int hue) {
    for (var i = 0; i < Constants.LightsConstants.LED_LENGTH; i++) {
      m_LEDBuffer.setHSV(i, hue, 255, m_blinkState ? 255 : 0);
    }
    if (m_timer.hasElapsed(Constants.LightsConstants.kBlinkDelay)) {
      m_blinkState = !m_blinkState;
      m_timer.reset();
    }
  }
  public void disableLights() {
    for (var i = 0; i < Constants.LightsConstants.LED_LENGTH; i++) {
      m_LEDBuffer.setLED(i, Color.kBlack);
    }

    setData();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
