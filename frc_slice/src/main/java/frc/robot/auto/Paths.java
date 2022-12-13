package frc.robot.auto;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Paths {

    private static Trajectory blueLeftTrajectory1;
    private static Path blueLeftTrajectoryPath1;
    private static String blueLeftPath1JSON;

    private static Trajectory blueLeftTrajectory2;
    private static Path blueLeftTrajectoryPath2;
    private static String blueLeftPath2JSON;

    private static Trajectory blueLeftTrajectory3;
    private static Path blueLeftTrajectoryPath3;
    private static String blueLeftPath3JSON;

    /*private static Trajectory redLeftTrajectory1;
    private static Path redLeftTrajectoryPath1;
    private static String redLeftPath1JSON;*/

    private static ArrayList<Trajectory> blueLeftPath;

    private static SendableChooser<ArrayList<Trajectory>> pathChooser;

    public static void createAutoPaths() {

         //Path string variable declarations
        blueLeftPath1JSON = "output/Blue Left Path 1.wpilib.json";
        blueLeftPath2JSON = "output/Blue Left Path 2.wpilib.json";
        blueLeftPath3JSON = "output/Blue Left Path 3.wpilib.json";
        /*blueLeftPath1JSON = "pathplanner/generatedJSON/Blue Left Path 1.wpilib.json";
        blueLeftPath2JSON = "pathplanner/generatedJSON/Blue Left Path 2.wpilib.json";
        blueLeftPath3JSON = "pathplanner/generatedJSON/Blue Left Path 3.wpilib.json";*/
        //redLeftPath1JSON = "pathplanner/generatedJSON/Red Left Path 1.wpilib.json";

        //Trajectory and path object declarations
        blueLeftTrajectory1 = new Trajectory();
        blueLeftTrajectoryPath1 = Filesystem.getDeployDirectory().toPath().resolve(blueLeftPath1JSON);
        blueLeftTrajectory2 = new Trajectory();
        blueLeftTrajectoryPath2 = Filesystem.getDeployDirectory().toPath().resolve(blueLeftPath2JSON);
        blueLeftTrajectory3 = new Trajectory();
        blueLeftTrajectoryPath3 = Filesystem.getDeployDirectory().toPath().resolve(blueLeftPath3JSON);
        //redLeftTrajectory1 = new Trajectory();
        //redLeftTrajectoryPath1 = Filesystem.getDeployDirectory().toPath().resolve(redLeftPath1JSON);

        //Path to trajecectory conversions
        try {
            blueLeftTrajectory1 = TrajectoryUtil.fromPathweaverJson(blueLeftTrajectoryPath1);
        } 
        catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + blueLeftPath1JSON, ex.getStackTrace());
        }
        try {
            blueLeftTrajectory2 = TrajectoryUtil.fromPathweaverJson(blueLeftTrajectoryPath2);
        }
        catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + blueLeftPath2JSON, ex.getStackTrace());
        }
        try {
            blueLeftTrajectory3 = TrajectoryUtil.fromPathweaverJson(blueLeftTrajectoryPath3);
        }
        catch(IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + blueLeftPath3JSON, ex.getStackTrace());
        }
        /*try {
            redLeftTrajectory1 = TrajectoryUtil.fromPathweaverJson(redLeftTrajectoryPath1);
        }
        catch(IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + redLeftPath1JSON, ex.getStackTrace());
        }*/

        blueLeftPath = new ArrayList<Trajectory>();

        blueLeftPath.add(blueLeftTrajectory1);
        blueLeftPath.add(blueLeftTrajectory2);
        blueLeftPath.add(blueLeftTrajectory3);

        pathChooser = new SendableChooser<>();

        //All of the options are blueLeftPath for now as placholders
        pathChooser.addOption("Blue Left", blueLeftPath);
        pathChooser.addOption("Blue Middle", blueLeftPath);
        pathChooser.addOption("Blue Right", blueLeftPath);
        pathChooser.addOption("Red Left", blueLeftPath);
        pathChooser.addOption("Red Middle", blueLeftPath);
        pathChooser.addOption("Red Right", blueLeftPath);

        pathChooser.setDefaultOption("Blue Left", blueLeftPath);

        Shuffleboard.getTab("SmartDashboard").add("Auto Selector", pathChooser);

    }

    public static ArrayList<Trajectory> getAutoPath() {

       return pathChooser.getSelected();

    }

    public static Trajectory returnPlaceholderTrajectory() {
        return new Trajectory();
    }
}