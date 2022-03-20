package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaCurrentGame;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.Tfod;

@Autonomous(name = "RedRight")
// Code for Red Alliance, right position(closer to warehouse)
public class RedRight extends LinearOpMode {

  private VuforiaCurrentGame vuforiaFreightFrenzy;
  private Tfod tfod;
  private DcMotor frontRightMotor;
  private DcMotor backLeftMotor;
  private DcMotor backRightMotor;
  private DcMotor frontLeftMotor;
  private DcMotor Armrotation;

  int LeftTarget;
  int RightTarget;
  int BackLeftTarget;
  double DRIVE_COUNTS_PER_INCH;
  double BackRightTarget;

  @Override
  public void runOpMode() {
    List<Recognition> _7BtfodRecognitionVariable_7D;
    int HD_COUNTS_PER_REV;
    double DRIVE_GEAR_REDUCTION;
    int DIAMETER;
    double WHEEL_CIRCUMFERENCE_MM;
    double DRIVE_COUNTS_PER_MM;
    int Level;

    vuforiaFreightFrenzy = new VuforiaCurrentGame();
    tfod = new Tfod();
    frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
    backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
    backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
    frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
    Armrotation = hardwareMap.get(DcMotor.class, "Arm rotation");

    // Put initialization blocks here.
    // Initialise Vuforia with Logitech webcam configured on the Control Hub
    vuforiaFreightFrenzy.initialize(
        "", // vuforiaLicenseKey
        hardwareMap.get(WebcamName.class, "Webcam 1"), // cameraName
        "webcamconfig.xml", // webcamCalibrationFilename
        true, // useExtendedTracking
        true, // enableCameraMonitoring
        VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES, // cameraMonitorFeedback
        0, // dx
        0, // dy
        0, // dz
        AxesOrder.XZY, // axesOrder
        0, // firstAngle
        0, // secondAngle
        0, // thirdAngle
        true);// useCompetitionFieldTargetLocations
    // Intinitialise Tensorflow Object Detection with Custom Model
    tfod.useModelFromFile("team_shipping_element.tflite", JavaUtil.createListWith("Shipping Element"), true, false, 320);
    tfod.initialize(vuforiaFreightFrenzy, (float) 0.6, true, true);
    tfod.activate();
    sleep(2000); // Give the model enough time to activate
    _7BtfodRecognitionVariable_7D = tfod.getRecognitions();
    // Reset the encoders of the motors
    frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    HD_COUNTS_PER_REV = 28; 
    DRIVE_GEAR_REDUCTION = 19.2;
    DIAMETER = 96;
    WHEEL_CIRCUMFERENCE_MM = DIAMETER * Math.PI;
    DRIVE_COUNTS_PER_MM = HD_COUNTS_PER_REV * (DRIVE_GEAR_REDUCTION / WHEEL_CIRCUMFERENCE_MM);
    DRIVE_COUNTS_PER_INCH = DRIVE_COUNTS_PER_MM * 25.4;
    backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    waitForStart();
    if (opModeIsActive()) {
      if (_7BtfodRecognitionVariable_7D.size() == 0) {
        Level = 3; // If it does not detect anything set arm level to three(shipping element not visible to camera)
      } else {
        if (((Recognition) JavaUtil.inListGet(_7BtfodRecognitionVariable_7D, JavaUtil.AtMode.FIRST, 0, false)).getLeft() < 250) {
          Level = 1; // If position of shipping element is more towards the left of the frame, set level to 1
        } else if (((Recognition) JavaUtil.inListGet(_7BtfodRecognitionVariable_7D, JavaUtil.AtMode.FIRST, 0, false)).getLeft() < 600) {
          Level = 2; // If position of shipping element is more towards the centre of the frame, set level to 2
        } else {
          Level = 3; // If position of shipping element is towards the right of the frame, set level to 3
        }
      }
      telemetry.addData("Left", _7BtfodRecognitionVariable_7D.getLeft()); // Print the left value to the telementry
      if (Level == 1) {
        drive2(-2710, 0.15, 0, 52.8, 52.8, 0); // Move the arm to encoder position -2710 (Level 1) and drive diagonally towards the shipping hub
      } else if (Level == 2) {
        drive2(-2105, 0.15, 0, 52.8, 52.8, 0); // Move the arm to encoder position -2105 (Level 2) and drive diagonally towards the shipping hub
      } else {
        drive2(-1275, 0.15, 0, 52.8, 52.8, 0); // Move the arm to encoder position -1275 (Level 3) and drive diagonally towards the shipping hub
      }
      drive(0.15, 18, 18, 18, 18);
      sleep(2000);
      drive(0.15, -14.5, -14.5, -14.5, -14.5);
      drive(0.15, 0, -52.8, -52.8, 0);
      drive(0.15, 20, -20, 20, -20);
      drive(0.15, -4.2, 4.2, 4.2, -4.2);
      drive(0.15, -40, -40, -40, -40);
      telemetry.update();
    }

    vuforiaFreightFrenzy.close();
    tfod.close();
  }

  /**
   * Describe this function...
   */
  private void drive2(int position, double power, int rightinches, double leftinches, double rightback, int leftback) {
    if (opModeIsActive()) {
      // Put run blocks here.
      LeftTarget = (int) (leftinches * DRIVE_COUNTS_PER_INCH + frontLeftMotor.getCurrentPosition());
      RightTarget = (int) (rightinches * DRIVE_COUNTS_PER_INCH + frontRightMotor.getCurrentPosition());
      BackLeftTarget = (int) (leftback * DRIVE_COUNTS_PER_INCH + backLeftMotor.getCurrentPosition());
      BackRightTarget = rightback * DRIVE_COUNTS_PER_INCH + backRightMotor.getCurrentPosition();
      Armrotation.setTargetPosition(position);
      Armrotation.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      Armrotation.setPower(1);
      frontRightMotor.setTargetPosition(RightTarget);
      frontLeftMotor.setTargetPosition(LeftTarget);
      backRightMotor.setTargetPosition((int) BackRightTarget);
      backLeftMotor.setTargetPosition(BackLeftTarget);
      backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      backRightMotor.setPower(power);
      frontLeftMotor.setPower(power);
      frontRightMotor.setPower(power);
      backLeftMotor.setPower(power);
      while (opModeIsActive() && (Armrotation.isBusy() || backRightMotor.isBusy() || frontLeftMotor.isBusy() || frontRightMotor.isBusy() || backLeftMotor.isBusy())) {
        // Put loop blocks here.
      }
      Armrotation.setPower(0);
      backRightMotor.setPower(0);
      frontLeftMotor.setPower(0);
      frontRightMotor.setPower(0);
      backLeftMotor.setPower(0);
    }
  }

  /**
   * Describe this function...
   */
  private void drive(double power, double rightinches, double leftinches, double rightback, double leftback) {
    if (opModeIsActive()) {
      // Put run blocks here.
      LeftTarget = (int) (leftinches * DRIVE_COUNTS_PER_INCH + frontLeftMotor.getCurrentPosition());
      RightTarget = (int) (rightinches * DRIVE_COUNTS_PER_INCH + frontRightMotor.getCurrentPosition());
      BackLeftTarget = (int) (leftback * DRIVE_COUNTS_PER_INCH + backLeftMotor.getCurrentPosition());
      BackRightTarget = rightback * DRIVE_COUNTS_PER_INCH + backRightMotor.getCurrentPosition();
      frontRightMotor.setTargetPosition(RightTarget);
      frontLeftMotor.setTargetPosition(LeftTarget);
      backRightMotor.setTargetPosition((int) BackRightTarget);
      backLeftMotor.setTargetPosition(BackLeftTarget);
      backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      backRightMotor.setPower(power);
      frontLeftMotor.setPower(power);
      frontRightMotor.setPower(power);
      backLeftMotor.setPower(power);
      while (opModeIsActive() && (backRightMotor.isBusy() || frontLeftMotor.isBusy() || frontRightMotor.isBusy() || backLeftMotor.isBusy())) {
        // Put loop blocks here.
      }
      backRightMotor.setPower(0);
      frontLeftMotor.setPower(0);
      frontRightMotor.setPower(0);
      backLeftMotor.setPower(0);
    }
  }
}
