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

public class Vector
{
	public float x;
	public float y;
	public float z;

	public Vector()
	{
		this.init(0.0f, 0.0f, 0.0f);
	}

	public Vector(float x, float y)
	{
		this.init(x, y, 0.0f);
	}

	public Vector(float x, float y, float z)
	{
		this.init(x, y, z);
	}

	protected void init(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector get()
	{
		return new Vector(this.x, this.y, this.z);
	}

	/** Arithmetic functions */
	public static Vector add(Vector v1, Vector v2)
	{
		return new Vector(v1.x+v2.x, v1.y+v2.y, v1.z+v2.z);
	}

	public static Vector sub(Vector v1, Vector v2)
	{
		return new Vector(v1.x-v2.x, v1.y-v2.y, v1.z-v2.z);
	}

	public static Vector mult(Vector v1, float m)
	{
		return new Vector(v1.x*m, v1.y*m, v1.z*m);
	}

	public static Vector div(Vector v1, float d)
	{
		return new Vector(v1.x/d, v1.y/d, v1.z/d);
	}

	public static float mag(Vector v1)
	{
		return (float)(Math.sqrt(mag2(v1)));
	}

	public float mag()
	{
		return mag(this);
	}

	public static float mag2(Vector v1)
	{
		return v1.x*v1.x + v1.y*v1.y + v1.z*v1.z;
	}

	public float mag2()
	{
		return mag2(this);
	}

	public static Vector normalize(Vector v1)
	{
		return div(v1, mag(v1));
	}

	public Vector normalize()
	{
		return div(this, mag(this));
	}

	public static Vector prep(Vector v1)
	{
		return new Vector(-v1.y, v1.x);
	}

	public Vector prep()
	{
		return prep(this);
	}

	public static float dist(Vector v1, Vector v2)
	{
		return (Vector.sub(v1, v2)).mag();
	}

	/* Vector products */
	public static float dot(Vector v1, Vector v2)
	{
		return v1.x*v2.x + v1.y*v2.y + v1.z*v2.z;
	}

	public float dot(Vector v2)
	{
		return dot(this, v2);
	}

	public static Vector cross(Vector v1, Vector v2)
	{
		return new Vector(
			v1.y * v2.z - v1.z * v2.y,
			v1.z * v2.x - v1.x * v2.z,
			v1.x * v2.y - v1.y * v2.x
			);
	}

	public Vector cross(Vector v2)
	{
		return cross(this, v2);
	}


	public static Vector polar2cart(float a, float r)
	{
		Vector out = new Vector();
		out.x = (float)(r * Math.cos(a));
		out.y = (float)(r * Math.sin(a));
		return out;
	}
}