package com.example.course;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;

public class Cube {
    private FloatBuffer vertexBuffer;
    private ShortBuffer indexBuffer;
    private FloatBuffer textureBuffer;
    private float[] vertices;
    private short[] indices = {
            0, 1, 2, 1, 3, 2,
            4, 5, 6, 5, 7, 6,
            8, 9, 10, 9, 11, 10,
            12, 13, 14, 13, 15, 14,
            16, 17, 18, 17, 19, 18,
            20, 21, 22, 21, 23, 22
    };

    private float[] textureCoords = {
            0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, // FRONT
            0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, // BACK
            0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, // LEFT
            0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, // RIGHT
            0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, // TOP
            0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f // BOTTOM
    };

    public Cube(float[] vertices) {
        this.vertices = vertices;

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);

        ByteBuffer tbb = ByteBuffer.allocateDirect(textureCoords.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        textureBuffer = tbb.asFloatBuffer();
        textureBuffer.put(textureCoords);
        textureBuffer.position(0);
    }

    public void draw(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);

        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }
}
