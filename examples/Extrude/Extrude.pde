import gsn.mesh.*;

Mesh mesh;
Extrusion e;

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
  
  e = new Extrusion();
  for(int i = 0; i < 6; i++)
  {
      Shape s = Shape.circle(0.0f, 0.0f, i*50.0f, (sin(i/QUARTER_PI)+1.0f)*50.0f + 20.0f, 8);
      s.rotate(i/77.0f);
      e.addShape(s);
  }

  mesh = e.getMesh(2, 0, 0);
  
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