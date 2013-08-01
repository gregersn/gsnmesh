import gsn.mesh.*;

Mesh mesh;
Loft loft;

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
  
  loft = new Loft();
  for(int i = 0; i < 10; i++)
  {
      Shape s = Shape.circle(0.0f, 0.0f, -200.0f + i*50.0f, (sin(i/QUARTER_PI)+1.0f)*50.0f + 20.0f, 8);
      s.rotate(i/PI);
      loft.addShape(s);
  }

  mesh = loft.getMesh(8, 1, 3);
  
}

void draw() {
  background(192);
  translate(width/2, height/2);
  rotateX(millis()/1000.0f);
  rotateY(millis()/1234.0f);
  showMesh(mesh);
}