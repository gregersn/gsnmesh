/**
 * ##library.name##
 * ##library.sentence##
 * ##library.url##
 *
 * Copyright ##copyright## ##author##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author      ##author##
 * @modified    ##date##
 * @version     ##library.prettyVersion## (##library.version##)
 */

package gsn.mesh;

import java.util.ArrayList;

class DummyObject
{
	ArrayList<Vector> vertices;

	DummyObject()
	{
		this.vertices = new ArrayList<Vector>();
	}

	public void addVertex(Vector p)
	{
		this.addPoint(p);
	}
	public void addPoint(Vector p)
	{
		this.vertices.add(p);
	}

	public Vector getVertex(int i)
	{
		return this.getPoint(i);
	}

	public Vector getPoint(int i)
	{
		if(this.vertices.size() > i)
		{
			//System.out.println("getPoint(): Getting point");
			return this.vertices.get(i);
		}
		else
		{
			System.out.println("getPoint(): Index out of range");
			return null;
		}
	}

	public ArrayList<Vector> getVertices()
	{
		return this.vertices;
	}

	public int getVertexCount()
	{
		return this.vertices.size();
	}

	public void rotate(float rads)
	{
		for(Vector p: this.vertices)
		{
			float nx = (float)(p.x * Math.cos(rads) - p.y * Math.sin(rads));
			float ny = (float)(p.x * Math.sin(rads) + p.y * Math.cos(rads));

			p.x = nx;
			p.y = ny;
		}
	}

}