import gsn.mesh.*;

Mesh mesh;
Shape s;

void showMesh(Mesh m)
{
	beginShape(TRIANGLES);
	for(Face f: m.getFaces())
	{
		for(Vector p: f.getVertices())
		{
			vertex(p.x, p.y, p.z);
		}
	}
	endShape();
}


void setup()
{
	size(500, 500, OPENGL);
	s = Shape.circle(0.0f, 0.0f, 0.0f, 100.0f, 10);
	mesh = new Mesh(s);
}

void draw()
{
  background(192);
  translate(width/2, height/2);
  rotateX(millis()/1000.0f);
  rotateY(millis()/1234.0f);
  showMesh(mesh);
}