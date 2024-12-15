package com.example.course;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Glass {
    public FloatBuffer mVertexBuffer;
    public int n = 0;
    private float red;
    private float green;
    private float blue;

    public Glass(float radius, float height, float r, float g, float b) {
        this.red = r;
        this.green = g;
        this.blue = b;

        int dtheta = 3;
        float DTOR = (float) (Math.PI / 180.0f);

        ByteBuffer byteBuf = ByteBuffer.allocateDirect(5000 * 3 * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        mVertexBuffer = byteBuf.asFloatBuffer();

        for (int theta = 0; theta <= 360; theta += dtheta) {
            float x1 = (float) (Math.cos(theta * DTOR) * radius);
            float z1 = (float) (Math.sin(theta * DTOR) * radius);

            mVertexBuffer.put(x1).put(0).put(z1);

            mVertexBuffer.put(x1).put(height).put(z1);

            n += 2;
        }

        mVertexBuffer.position(0);
    }

    public void onDrawFrame(GL10 gl) {
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        gl.glFrontFace(GL10.GL_CCW);
        gl.glDisable(GL10.GL_CULL_FACE);
        gl.glColor4f(red, green, blue, 0.5f);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);

        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_BLEND);
    }
    public void onDrawWater(GL10 gl) {
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        gl.glFrontFace(GL10.GL_CCW);
        gl.glDisable(GL10.GL_CULL_FACE);
        gl.glColor4f(red, green, blue, 0.8f);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);

        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_BLEND);
    }
}
