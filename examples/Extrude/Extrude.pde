import gsn.mesh.*;

Mesh mesh;
Extrusion e;

void vertex(Vector p)
{
	vertex(p.x, p.y, p.z);
}

void showMesh(Mesh m)
{
	for(Face f: m.getFaces())
	{
    beginShape();
		for(Vector p: f.getVertices())
		{
			vertex(p);
		}
    endShape(CLOSE);
	}
}

void setup() {
  size(600, 600, OPENGL);
  smooth();
  
  e = new Extrusion();
  e.setVector(0.0f, 0.0f, 300.0f);
  Shape s = Shape.circle(0.0f, 0.0f, -50.0f, 100.0f, 16);
  e.setShape(s);
  
  mesh = e.getMesh(16, 0, 3);
  
}

void draw() {
  background(192);
  translate(width/2, height/2);
  rotateX(millis()/1000.0f);
  rotateY(millis()/1234.0f);
  showMesh(mesh);
  //fill(255);
  //text("Mordi!", 40, 200);
}