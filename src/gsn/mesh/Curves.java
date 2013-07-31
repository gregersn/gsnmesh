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

public class Curves
{
	/**
	*	Three dimensional catmull rom spline
	*/
	public static Vector catmullRomSpline(Vector a, Vector b, Vector c, Vector d, float t)
	{
		return new Vector(
			catmullRomSpline(a.x, b.x, c.x, d.x, t),
			catmullRomSpline(a.y, b.y, c.y, d.y, t),
			catmullRomSpline(a.z, b.z, c.z, d.z, t)
			);
	}

	/**
	*	Returns a point a long a (normalized) Catmull Rom spline
	*	Catmull Rom Spline taken from http://www.lighthouse3d.com/tutorials/maths/catmull-rom-spline/
	*	Call one time for each x, y and z to do multidimensional
	*	
	*	@param	a 	First control point
	*	@param	b 	Start point
	*	@param	c 	End point
	*	@param 	d 	Second control point
	*	@param 	t 	Value between 0.0f..1.0f to specify point
	*	@return 	float
	*	
	*/
	public static float catmullRomSpline(float a, float b, float c, float d, float t)
	{
		double c1, c2, c3, c4;

		c1 =  1.0 * b;
		c2 = -0.5 * a + 0.5 * c;
		c3 =  1.0 * a - 2.5 * b + 2.0 * c + -0.5 * d;
		c4 = -0.5 * a + 1.5 * b - 1.5 * c +  0.5 * d;

		return (float)(((c4 * t + c3) * t + c2) * t + c1);
	}
}