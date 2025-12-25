package io.github.team10.escapefromuni;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mockito.Mockito;

/**
 * Base class for all headless LibGDX tests with asset loading support.
 * This sets out the base design for the entire test suite. All of our tests are inheriting methods from the functions in this file. 
 */
public abstract class HeadlessTestRunner {
    private static Application application;
    
    @BeforeClass
    public static void setupHeadlessApplication() {
        // Create mock GL objects to prevent NullPointerExceptions during tests
        Gdx.gl = Mockito.mock(GL20.class);
        Gdx.gl20 = Mockito.mock(GL20.class);
        
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        
        // A script exists in the CI pipeline to fetch all of the images and other assets so this shouldn't be an issue when running tests that need them (in theory at least), but there's no need to make any changes here.
        // If there's any issues with the fetching process, just edit the YAML and it should work with no issues. 
        // Otherwise we are going to have to mess around with this config below. 
        application = new HeadlessApplication(new ApplicationListener() {
            @Override public void create() {}
            @Override public void resize(int width, int height) {}
            @Override public void render() {}
            @Override public void pause() {}
            @Override public void resume() {}
            @Override public void dispose() {}
        }, config);
        
    }
    
    @AfterClass
    // This is only after all of our testing is done and it basically just cleans up after the tests are finished and have been written to the HTML file.
    public static void cleanupHeadlessApplication() {
        if (application != null) {
            application.exit();
            application = null;
        }
    }
}