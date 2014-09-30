package jmini3d.geometry;

import jmini3d.GpuObjectStatus;

public class SpriteGeometry extends Geometry {
	float[] vertex;
	float[] uvs;
	short[] faces;

	int vertexPointer = 0;
	int facePointer = 0;

	public SpriteGeometry(int spriteCount) {
		vertex = new float[3 * 4 * spriteCount];
		uvs = new float[2 * 4 * spriteCount];
		faces = new short[3 * 2 * spriteCount];
	}

	public short addVertex(float x, float y, float z, float u, float v) {
		vertex[vertexPointer * 3] = x;
		vertex[vertexPointer * 3 + 1] = y;
		vertex[vertexPointer * 3 + 2] = z;

		uvs[vertexPointer * 2] = u;
		uvs[vertexPointer * 2 + 1] = v;

		vertexPointer++;
		return (short) (vertexPointer - 1);
	}

	public void addFace(short a, short b, short c) {
		faces[facePointer * 3] = a;
		faces[facePointer * 3 + 1] = b;
		faces[facePointer * 3 + 2] = c;

		facePointer++;
	}

	public void addSprite(float x1, float y1, float x2, float y2) {
		addSprite(x1, y1, x2, y2, 0, 0, 1, 1);
	}

	public void addSprite(float x1, float y1, float x2, float y2, float uvx1, float uvy1, float uvx2, float uvy2) {
		short _leftBack = addVertex(x1, y1, 0, uvx1, uvy1);
		short _rightBack = addVertex(x2, y1, 0, uvx2, uvy1);
		short _leftFront = addVertex(x1, y2, 0, uvx1, uvy2);
		short _rightFront = addVertex(x2, y2, 0, uvx2, uvy2);

		addFace(_leftBack, _leftFront, _rightBack);
		addFace(_rightBack, _leftFront, _rightFront);
	}

	public void setSpritePosition(int index, float x1, float y1, float x2, float y2) {
		vertex[index * 12] = x1;
		vertex[(index * 12) + 1] = y1;
		vertex[(index * 12) + 3] = x2;
		vertex[(index * 12) + 4] = y1;
		vertex[(index * 12) + 6] = x1;
		vertex[(index * 12) + 7] = y2;
		vertex[(index * 12) + 9] = x2;
		vertex[(index * 12) + 10] = y2;

		status &= ~GpuObjectStatus.VERTICES_UPLOADED;
	}

	public void setUv(int index, float u, float v) {
		uvs[index << 1] = u;
		uvs[(index << 1) + 1] = v;

		status &= ~GpuObjectStatus.UVS_UPLOADED;
	}

	public float[] vertex() {
		return vertex;
	}

	public float[] normals() {
		return null;
	}

	public float[] uvs() {
		return uvs;
	}

	public short[] faces() {
		return faces;
	}
}