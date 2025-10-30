package utils;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import javax.swing.*;
import java.awt.*;

public class WaveformPlotter implements GLEventListener {
    private final int[] waveform;
    private final String title;

    public WaveformPlotter(int[] waveform, String title) {
        this.waveform = waveform;
        this.title = title;
    }

    public static void displayWaveform(int[] waveform, String title) {
        GLProfile glp = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);

        WaveformPlotter plotter = new WaveformPlotter(waveform, title);
        canvas.addGLEventListener(plotter);

        JFrame frame = new JFrame("Digital Signal - " + title);
        frame.setSize(900, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas, BorderLayout.CENTER);
        frame.setVisible(true);

        new FPSAnimator(canvas, 60).start();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        drawable.getGL().getGL2().glClearColor(1f, 1f, 1f, 1f);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {}

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glColor3f(0f, 0f, 0f);

        gl.glBegin(GL2.GL_LINES);
        gl.glVertex2f(-1f, 0f);
        gl.glVertex2f(1f, 0f);
        gl.glEnd();

        gl.glColor3f(0.2f, 0.2f, 1f);
        gl.glLineWidth(2f);
        gl.glBegin(GL2.GL_LINE_STRIP);

        int n = waveform.length;
        for (int i = 0; i < n; i++) {
            float x = (float)(2.0 * i / n - 1.0);
            float y = (float)waveform[i] * 0.5f;
            gl.glVertex2f(x, y);
        }
        gl.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        float aspect = (float) w / h;
        gl.glOrtho(-1 * aspect, 1 * aspect, -1, 1, -1, 1);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
}
