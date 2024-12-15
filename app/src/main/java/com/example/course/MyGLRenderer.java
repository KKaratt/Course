package com.example.course;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;

import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

class MyGLRenderer implements GLSurfaceView.Renderer {
    static public int[] texture_name = {
            R.drawable.watermelon,
            R.drawable.apple,
            R.drawable.lemon,
            R.drawable.orange,
            R.drawable.table
    };

    private float x0 = 0f;
    private float z0 = 0f;
    private float a = 0;
    private float angle = 0;
    private float speed = 0.1f;
    static public int[] textures = new int[texture_name.length];
    Context c;

    private final Sphere watermelon = new Sphere(3f);
    private final Glass cup = new Glass(1.3f, 4f, 0.4f, 0.4f, 0.4f);
    private final Glass water = new Glass(1.1f, 3.5f, 0.0f, 0.5f, 1.0f);
    private final Sphere apple = new Sphere(1.5f);
    private final Sphere lemon = new Sphere(1.5f);
    private final Sphere orange = new Sphere(1.5f);
    private final Cube cube = new Cube(new float[]{
            // FRONT
            -1.0f, -1.0f, 1.0f,  1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,   1.0f, 1.0f, 1.0f,
            // BACK
            1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,  -1.0f, 1.0f, -1.0f,
            // LEFT
            -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, -1.0f,  -1.0f, 1.0f, 1.0f,
            // RIGHT
            1.0f, -1.0f, 1.0f,   1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, 1.0f,    1.0f, 1.0f, -1.0f,
            // TOP
            -1.0f, 1.0f, 1.0f,   1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, -1.0f,  1.0f, 1.0f, -1.0f,
            // BOTTOM
            -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,  1.0f, -1.0f, 1.0f
    });

    public MyGLRenderer(Context context) {
        c = context;
    }

    private void loadGLTexture(GL10 gl) {
        gl.glGenTextures(4, textures, 0);
        for (int i = 0; i < texture_name.length; ++i) {
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[i]);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
            InputStream is = c.getResources().openRawResource(texture_name[i]);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
            bitmap.recycle();
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glLoadIdentity();
        gl.glOrthof(-10, 10, -10, 10, -10, 100);
        GLU.gluPerspective(gl, 45, 1, -10f, 10.f);
        GLU.gluLookAt(gl, 5.0f, 1.5f, 2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glScalef(1f, 2f, 1);
        loadGLTexture(gl);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {}

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glClearColor(1.0f, 0.980f, 0.804f, 1.0f);
        gl.glPopMatrix();

        gl.glPushMatrix(); // арбуз
        gl.glTranslatef((float) (0f * Math.cos(a) - 1f * Math.sin(a)),
                0.0f,
                (float) (1f * Math.cos(a) + 0f * Math.sin(a)));
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glTexCoordPointer(1, GL10.GL_FLOAT, 0, watermelon.textureBuffer);
        gl.glColor4f(1, 1 ,1, 1);
        gl.glScalef(.6f, .6f, .6f);
        gl.glRotatef(angle, 0.0f, -1.0f, 0.0f);
        watermelon.onDrawFrame(gl);
        gl.glPopMatrix();

        gl.glPushMatrix(); // яблоко
        gl.glTranslatef((float) (3f * Math.cos(a) - 3f * Math.sin(a)),
                -1.15f,
                (float) (3f * Math.cos(a) + 3f * Math.sin(a)));
        gl.glScalef(.44f, .44f, .44f);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[1]);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, apple.textureBuffer);
        gl.glColor4f(1, 1 ,1, 1);
        gl.glRotatef(angle, 0.0f, -1.0f, 0.0f);
        apple.onDrawFrame(gl);
        gl.glPopMatrix();

        gl.glPushMatrix(); // лимон
        gl.glTranslatef((float) (-2f * Math.cos(a) - -3f * Math.sin(a)),
                -1.50f,
                (float) (-3f * Math.cos(a) + -2f * Math.sin(a)));
        gl.glScalef(.20f, .20f, .36f);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[2]);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, lemon.textureBuffer);
        gl.glColor4f(1, 1 ,1, 1);
        gl.glRotatef(angle, 0.0f, -1.0f, 0.0f);
        lemon.onDrawFrame(gl);
        gl.glPopMatrix();

        gl.glPushMatrix(); // апельсин
        gl.glTranslatef((float) (-3.8f * Math.cos(a) - -1f * Math.sin(a)),
                -1.05f,
                (float) (-1f * Math.cos(a) + -3.8f * Math.sin(a)));
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[3]);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, orange.textureBuffer);
        gl.glScalef(.5f, .5f, .5f);
        gl.glColor4f(1, 1 ,1, 1);
        gl.glRotatef(angle, 0.0f, -1.0f, 0.0f);
        orange.onDrawFrame(gl);
        gl.glPopMatrix();

        gl.glPushMatrix(); // столешница
        gl.glTranslatef(0.0f, -2.0f, 0f);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[4]);
        gl.glScalef(5f, 0.2f, 5f);
        gl.glColor4f(1, 1 ,1, 1.0f);
        gl.glRotatef(angle, 0.0f, -1.0f, 0f);
        cube.draw(gl);
        gl.glPopMatrix();

        gl.glPushMatrix(); // ножка передняя слева
        gl.glTranslatef((float) (-4.6f * Math.cos(a) - 4.6f * Math.sin(a)),
                -3.0f,
                (float) (4.6f * Math.cos(a) + -4.6f * Math.sin(a)));
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[4]);
        gl.glScalef(0.3f, 1f, 0.3f);
        gl.glColor4f(1, 1 ,1, 1.0f);
        gl.glRotatef(angle, 0.0f, -1.0f, 0f);
        cube.draw(gl);
        gl.glPopMatrix();

        gl.glPushMatrix(); // ножка передняя справа
        gl.glTranslatef((float) (4.6f * Math.cos(a) - 4.6f * Math.sin(a)),
                -3.0f,
                (float) (4.6f * Math.cos(a) + 4.6f * Math.sin(a)));
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[4]);
        gl.glScalef(0.3f, 1f, 0.3f);
        gl.glColor4f(1, 1 ,1, 1.0f);
        gl.glRotatef(angle, 0.0f, -1.0f, 0f);
        cube.draw(gl);
        gl.glPopMatrix();

        gl.glPushMatrix(); // ножка задняя справа
        gl.glTranslatef((float) (4.6f * Math.cos(a) - -4.6f * Math.sin(a)),
                -3.0f,
                (float) (-4.6f * Math.cos(a) + 4.6f * Math.sin(a)));
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[4]);
        gl.glScalef(0.3f, 1f, 0.3f);
        gl.glColor4f(1, 1 ,1, 1.0f);
        gl.glRotatef(angle, 0.0f, -1.0f, 0f);
        cube.draw(gl);
        gl.glPopMatrix();

        gl.glPushMatrix(); // ножка задняя слева
        gl.glTranslatef((float) (-4.6f * Math.cos(a) - -4.6f * Math.sin(a)),
                -3.0f,
                (float) (-4.6f * Math.cos(a) + -4.6f * Math.sin(a)));
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[4]);
        gl.glScalef(0.3f, 1f, 0.3f);
        gl.glColor4f(1, 1 ,1, 1.0f);
        gl.glRotatef(angle, 0.0f, -1.0f, 0f);
        cube.draw(gl);
        gl.glPopMatrix();

        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisable(GL10.GL_TEXTURE_2D);

        gl.glPushMatrix(); //вода в стакане
        gl.glTranslatef((float) (3f * Math.cos(a) - -2f * Math.sin(a)),
                -1.75f,
                (float) (-2f * Math.cos(a) + 3f * Math.sin(a)));
        gl.glScalef(.44f, .44f, .44f);
        gl.glRotatef(angle, 0.0f, 1.0f, 0.0f);
        water.onDrawWater(gl);
        gl.glPopMatrix();

        gl.glPushMatrix(); //стакан
        gl.glTranslatef((float) (3f * Math.cos(a) - -2f * Math.sin(a)),
                -1.75f,
                (float) (-2f * Math.cos(a) + 3f * Math.sin(a)));
        gl.glScalef(.44f, .44f, .44f);
        gl.glRotatef(angle, 0.0f, 1.0f, 0.0f);
        cup.onDrawFrame(gl);
        gl.glPopMatrix();

        angle += speed;
        a += 0.001745 % Float.MAX_VALUE;
    }
}
