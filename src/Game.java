import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL; 

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;


public class Game 
{
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback; 
    private float red = 0.0f; 
    private float green = 0.0f; 
    private float blue = 0.0f; 
    private long Window; 
	
	
    public Game() 
	{
        // Set the error callback
        errorCallback = GLFWErrorCallback.createPrint(System.err);
        glfwSetErrorCallback(errorCallback);

        // Initialize GLFW
        if (!glfwInit()) 
		{
            throw new IllegalStateException("Unable to initialize GLFW");
        }

		

		// Create a window
		Window = glfwCreateWindow(800, 600 , "Crafting" , NULL , NULL );
		
		// Check if the window was created successfully
		if (Window == NULL)
		{
			System.out.println("Failed to create the GLFW window");
		}

        // Make the OpenGL context current
        glfwMakeContextCurrent(Window);

        // Create OpenGL capabilities
        GL.createCapabilities();

        // Enable v-sync
        glfwSwapInterval(1);

        // Initialize the key callback
        keyCallback = new GLFWKeyCallback() 
		{
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) 
			{
                if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) 
				{
                    glfwSetWindowShouldClose(window, true);
                }
				else if (key == GLFW_KEY_C && action == GLFW_PRESS)
					{
                    // Change the color values randomly
                    red = (float) Math.random();
                    green = (float) Math.random();
                    blue = (float) Math.random();
					System.out.println("Color changed");
					System.out.println("Red: " + red + " Green: " + green + " Blue: " + blue);
					System.out.println( (int) glfwGetTime() );
                }
            }
        };

        glfwSetKeyCallback(Window, keyCallback); // Ensure this is set before the game loop

		// Game loop
		final int TARGET_FPS = 60;
		final double FRAME_TIME = 1.0 / TARGET_FPS; // Time per frame in seconds

		while (!glfwWindowShouldClose(Window)) {
			double startTime = glfwGetTime();

			// Poll for window events
			glfwPollEvents();

			// Set the clear color
			glClearColor(red, green, blue, 0.0f);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			// Swap the color buffers
			glfwSwapBuffers(Window);

			// Frame rate limiter
			double endTime = glfwGetTime();
			double elapsedTime = endTime - startTime;
			if (elapsedTime < FRAME_TIME) {
				try {
					Thread.sleep((long) ((FRAME_TIME - elapsedTime) * 1000)); // Convert to milliseconds
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		// Clean up and terminate GLFW
		glfwDestroyWindow(Window);
		glfwTerminate();
		errorCallback.free();
    }

    public static void main(String[] args) 
	{
        new Game();
    }
}
