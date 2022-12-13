import static org.junit.Assert.*;

import edu.wpi.first.hal.HAL;
//import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.simulation.JoystickSim;
import edu.wpi.first.wpilibj.simulation.PWMSim;
import frc.robot.Constants;

import org.junit.*;

public class DrivetrainTest {
    PWMSim simMotor;
    public static final double DELTA = 1e-2; // acceptable deviation range

    //Define Joysticks
    public static JoystickSim leftJoystick = new JoystickSim(Constants.RobotContainer_LEFT_JOYSTICK_PORT);
    public static JoystickSim rightJoystick = new JoystickSim(Constants.RobotContainer_RIGHT_JOYSTICK_PORT);

    @Before // this method will run before each test
    public void setup() {
        assert HAL.initialize(500, 0); // initialize the HAL, crash if failed
        
        simMotor = new PWMSim(0); // create our simulation PWM motor controller

    }

    @After // this method will run after each test
    public void shutdown() throws Exception {
    }
  
    // marks this method as a test
    public void testForward() {
        /**
         * TODO: Implement motor tests
         */

        // Act as if a user is pushing the forward button
        // Assert in case the motor is NOT doing what you expect
        assertEquals(0.0, 0.0, DELTA); // make sure that the value set to the motor is 0
    }

}
