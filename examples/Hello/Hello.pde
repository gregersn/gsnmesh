import gsn.mesh.*;

Mesh mesh;

void vertex(Vector p)
{
	vertex(p.x, p.y, p.z);
}

void showMesh(Mesh m)
{
	beginShape(QUADS);
	for(Face f: m.getFaces())
	{
		for(Vector p: f.getVertices())
		{
			vertex(p);
		}
	}
	endShape();
}

void setup() {
  size(600, 600, OPENGL);
  smooth();
  
  mesh = Mesh.cube(20);
}

void draw() {
  background(192);
  translate(width/2, height/2);
  rotateX(millis()/1000.0f);
  rotateY(millis()/1234.0f);
  showMesh(mesh);
}