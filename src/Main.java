/**
 * Angry Bullet Game
 * @author Salih Can ERER, Student ID : 2022400174
 * @since Date 20.03.2024
 */
import java.awt.event.KeyEvent; // For detecting user keyboard input
import java.awt.Font; // For utilizing fancy fonts

public class Main {
    public static void main(String[] args) {

        // Initialize dimensions and constants
        int width = 1600; //screen width
        int height = 800; // screen height
        double gravity = 9.80665; // gravity

        // Given bullet properties
        double bulletVelocity = 180; // initial velocity
        double bulletAngle = 45.0; // initial angle

        // Box coordinates for obstacles and targets
        // Each row stores a box containing the following information:
        // x and y coordinates of the lower left rectangle corner, width, and height

        // Initialize obstacle array
        double[][] obstacleArray = {
                {1200, 0, 60, 220},
                {1000, 0, 60, 160},
                {600, 0, 60, 80},
                {600, 180, 60, 160},
                {220, 0, 120, 180}
        };

        double[][] targetArray = {
                {1160, 0, 30, 30},
                {730, 0, 30, 30},
                {150, 0, 20, 20},
                {1480, 0, 60, 60},
                {340, 80, 60, 30},
                {1500, 600, 60, 60}
        };

        // Arbitrarily generated array:
        //
        // // Initialize obstacle array
        // double[][] obstacleArray = {
        //         {1238, 388, 57, 48},
        //         {1297, 701, 94, 66},
        //         {896, 657, 31, 67},
        //         {1422, 297, 31, 58},
        //         {1172, 293, 33, 71}
        // };
        //
        // // Initialize target array
        // double[][] targetArray = {
        //         {1360, 72, 26, 39},
        //         {1490, 247, 20, 15},
        //         {613, 690, 41, 25},
        //         {1526, 704, 33, 17},
        //         {956, 427, 47, 30}
        // };

        // Initialize StdDraw canvas and scaling
        StdDraw.setCanvasSize(width, height); // Set canvas size
        StdDraw.setXscale(0, width); // Set width scaling
        StdDraw.setYscale(0, height); // Set height scaling
        StdDraw.enableDoubleBuffering(); // Enable double buffering to smoothen the animation


        //Converting given angle to radians and calculating components of the angle
        double angleRadians = Math.toRadians(bulletAngle); // Convert to radians

        double velocityX = bulletVelocity * Math.cos(angleRadians); // Evaluate X component of the velocity
        double velocityY = bulletVelocity * Math.sin(angleRadians); // Evaluate Y component of the velocity

        double dt = 0.1; // Time quantum

        // Initializing the font
        Font font = new Font("Arial", Font.BOLD, 20);
        StdDraw.setFont(font);

        boolean gameRunning = true; //Game control flag

        while (gameRunning) {
            // Beginning of the game loop

            // Initialize starting variables
            double x0 = 120; // Initialize X
            double y0 = 120; // Initialize Y

            bulletVelocity = 180; // Resetting velocity to the stating value
            bulletAngle = 45; // Resetting angle to the initial value

            double scalingFactor = 0.8; // Line scaling factor for adjusting the speed of growth

            // Awaiting player input to start the simulation
            while (!StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
                // Beginning of the game set-up loop

                // Clearing the canvas and declaring it to be white
                StdDraw.clear(StdDraw.WHITE);

                // Drawing obstacles
                StdDraw.setPenColor(StdDraw.DARK_GRAY); // Picking a dull color for obstacles

                // Calculating necessary center coordinates of obstacles and drawing them
                for (double[] rect : obstacleArray) {
                    // Calculating the center, half-width and half-length of the obstacles
                    StdDraw.filledRectangle(rect[0] + rect[2] / 2, rect[1] + rect[3] / 2, rect[2] / 2, rect[3] / 2);
                }

                // Drawing targets
                StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE); // Picking a fancy color for targets

                // Calculating necessary center coordinates of targets and drawing them
                for (double[] rect : targetArray) {
                    // Calculating the center, half-width and half-length of the targets
                    StdDraw.filledRectangle(rect[0] + rect[2] / 2, rect[1] + rect[3] / 2, rect[2] / 2, rect[3] / 2);
                }

                double minLength = 40; // Define the minimum length of the line

                // Check if the length is less than the minimum length, using the predetermined corresponding velocity
                if (bulletVelocity < 145) {
                    // Calculate the minimum scaling factor required to achieve the minimum length, utilizing given current velocityX and velocityY.
                    // Use the original velocities to get the correct scaling factor that ensures the minimum length.
                    scalingFactor = minLength / (Math.pow(Double.MIN_VALUE, Double.MIN_VALUE)  +  Math.sqrt(Math.pow(velocityX, 2) + Math.pow(velocityY, 2))); // Declare scaling factor
                }

                StdDraw.setPenColor(StdDraw.BLACK); // Setting pen color
                StdDraw.setPenRadius(0.01); // Setting pen radius
                StdDraw.filledSquare(60, 60, 60); // Draw the shooting base

                // Draw the shooting line with a scaling factor in order to adjust the length
                StdDraw.line(120, 120, (120 + velocityX * scalingFactor), (120 + velocityY * scalingFactor));

                // Display the velocity and angle on screen
                String aString = String.format("a: %s", bulletAngle); // Initializing angle string
                String vString = String.format("v: %s", bulletVelocity); // Initializing velocity string

                StdDraw.setPenColor(StdDraw.WHITE); // Set pen color to white
                StdDraw.text(55, 70, aString); // Display angle
                StdDraw.text(60, 40, vString); // Display velocity
                StdDraw.show(); // Display the current state of the game

                // Handle user input to adjust the bullet's velocity and angle
                if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
                    bulletVelocity = bulletVelocity - 1; // Decrease velocity
                    scalingFactor -= 0.01; // Adjust scaling factor
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
                    bulletVelocity = bulletVelocity + 1; // Increase velocity
                    scalingFactor += 0.01; // Adjust scaling factor
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
                    bulletAngle = bulletAngle + 1; // Increase angle
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
                    bulletAngle = bulletAngle - 1; // Decrease angle
                }

                // Recalculate the angle and components of velocity, and scale them with an appropriate constant
                angleRadians = Math.toRadians(bulletAngle); // Convert degrees to radians
                velocityX = bulletVelocity * 0.578 * Math.cos(angleRadians); // Calculate X component with a scalar
                velocityY = bulletVelocity * 0.578 * Math.sin(angleRadians); // Calculate Y component with a scalar
                StdDraw.pause(50); // Pause to control the speed of the loop
            }

            // Initialize variables to track the current state of the game
            boolean collision = false; // Tracks if the bullet hits an obstacle
            boolean victory = false; // Tracks if the bullet hits a target
            boolean maxXreached = false; // Tracks if the bullet reaches the right end of the screen
            boolean isGround = false; // Tracks if the bullet hits the ground

            double lastDrawTime = System.currentTimeMillis(); // Getting the current time
            double drawInterval = 1000 / 30.0; // Targeting 30 FPS for aesthetic trajectory drawing

            // Simulation loop for the flight of the bullet
            while (!collision && !victory && !maxXreached && !isGround) {
                // Beginning of the simulation and collusion detection loop

                // Update bullet position based on velocity and gravity
                double newX0 = x0 + velocityX * dt; // Update X position
                double newY0 =  y0 + velocityY * dt - 0.5 * gravity * dt * dt; // Update Y position considering the gravity
                velocityY = velocityY - gravity * dt; // Apply gravity

                // Reset pen settings for drawing the trajectory
                StdDraw.setPenRadius(); // Reset pen radius
                StdDraw.setPenColor(StdDraw.BLACK); // Declare pen color

                StdDraw.line(x0, y0, newX0, newY0); // Draw line segment
                StdDraw.show(); // Display the current state of the game
                StdDraw.pause(10); // Short pause for smoothness purposes

                // Update bullet's position for the next iteration
                x0 = newX0; // Update X position
                y0 = newY0; // Update Y position

                // Periodically draw the bullet
                double currentTime = System.currentTimeMillis(); // Get current time
                double deltaTime = currentTime - lastDrawTime; // Get time difference

                // Check for the elapsed time between the last draw
                if (deltaTime >= drawInterval) {
                    lastDrawTime = currentTime; // Update last draw time
                    StdDraw.filledCircle(x0, y0, 4); // Draw bullet
                    StdDraw.show(); // Display the current state of the game
                }

                // Check for collision with obstacles
                for (double[] rect : obstacleArray) {
                    if (x0 >= rect[0] && x0 <= rect[0] + rect[2] && y0 >= rect[1] && y0 <= rect[1] + rect[3]) {
                        collision = true; // Set collusion flag
                        StdDraw.filledCircle(x0, y0, 4); // Mark collusion point
                        // Handle game state after collusion with an obstacle occurs
                        // Display a given message and wait for the user to press 'r' to restart
                        while (!StdDraw.isKeyPressed(82) && !StdDraw.isKeyPressed(114)) {
                            StdDraw.textLeft(10, 780, "Hit an obstacle. Press 'r' to shoot again."); // Display the message
                            StdDraw.show(); // Show the message on the screen
                            StdDraw.pause(10); // Pause briefly to check the key is whether pressed or not.
                        }
                    }
                }

                // Check for collusion with targets
                for (double[] rect : targetArray) {
                    if (x0 >= rect[0] && x0 <= rect[0] + rect[2] && y0 >= rect[1] && y0 <= rect[1] + rect[3]) {
                        victory = true; // Set victory flag
                        StdDraw.filledCircle(x0, y0, 4); // Mark victory point
                        // Handle game state after collusion with a target occurs
                        // Display a given message and wait for the user to press 'r' to restart
                        while (!StdDraw.isKeyPressed(82) && !StdDraw.isKeyPressed(114)) {
                            StdDraw.textLeft(10, 780, "Congratulations: You hit the target!"); // Show the message on the screen
                            StdDraw.show(); // Show the message on the screen
                            StdDraw.pause(10); // Pause briefly to check the key is whether pressed or not.
                        }
                    }
                }

                // Check for the case of bullet hitting the ground
                if (y0 <= 0) {
                    isGround = true; // Set ground collusion flag
                    StdDraw.filledCircle(x0, y0, 4); // Mark ground collusion point
                    // Handle game state after collusion with the ground occurs
                    // Display a given message and wait for the user to press 'r' to restart
                    while (!StdDraw.isKeyPressed(82) && !StdDraw.isKeyPressed(114)) {
                        StdDraw.textLeft(10, 780, "Hit the ground. Press 'r' to shoot again."); // Show the message on the screen
                        StdDraw.show(); // Show the message on the screen
                        StdDraw.pause(10); // Pause briefly to check the key is whether pressed or not.
                    }
                }

                // Check for bullet reaching the maximum X boundary (right side of the screen)
                if (x0 >= 1600) {
                    maxXreached = true; // Set max X reached flag
                    StdDraw.filledCircle(x0, y0, 4); // Mark the point where max X is reached
                    // Handle game state after collusion with the right side of the screen occurs
                    // Display a given message and wait for the user to press 'r' to restart
                    while (!StdDraw.isKeyPressed(82) && !StdDraw.isKeyPressed(114)) {
                        StdDraw.textLeft(10, 780, "Max X reached. Press 'r' to shoot again."); // Show the message on the screen
                        StdDraw.show(); // Show the message on the screen
                        StdDraw.pause(10); // Pause briefly to check the key is whether pressed or not.
                    }
                }
            }
        }
    }
}