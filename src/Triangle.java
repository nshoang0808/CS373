// Triangle class
// defines a Triangle shape

import javax.vecmath.*;

public class Triangle extends Shape {
	private Vector3f p0, p1, p2;	// three vertices make a triangle
	private Vector3f n0, n1, n2;	// normal at each vertex

	public Triangle() {
	}
	public Triangle(Vector3f _p0, Vector3f _p1, Vector3f _p2, Material mat) {
		p0 = new Vector3f(_p0);
		p1 = new Vector3f(_p1);
		p2 = new Vector3f(_p2);
		material = mat;
		Vector3f normal = new Vector3f();
		Vector3f v1 = new Vector3f();
		Vector3f v2 = new Vector3f();
		v1.sub(p1, p0);
		v2.sub(p2, p0);
		normal.cross(v1, v2);
		normal.normalize();				// compute default normal:
		n0 = new Vector3f(normal);		// the normal of the plane defined by the triangle
		n1 = new Vector3f(normal);
		n2 = new Vector3f(normal);
	}
	public Triangle(Vector3f _p0, Vector3f _p1, Vector3f _p2,
					Vector3f _n0, Vector3f _n1, Vector3f _n2,
					Material mat) {
		p0 = new Vector3f(_p0);
		p1 = new Vector3f(_p1);
		p2 = new Vector3f(_p2);
		material = mat;
		n0 = new Vector3f(_n0);		// the normal of the plane defined by the triangle
		n1 = new Vector3f(_n1);
		n2 = new Vector3f(_n2);
	}
	
	float determinant(float a, float b, float c, float d, float e, float f, float g, float h, float i) {
		return a*e*i-a*h*f-b*d*i+b*g*f+c*d*h-c*g*e;
	}
	public HitRecord hit(Ray ray, float tmin, float tmax) {
		/* YOUR WORK HERE: complete the triangle's intersection routine
		 * Normal should be computed by a bilinear interpolation from n0, n1 and n2
		 * using the barycentric coordinates: alpha, beta, (1.0 - alpha - beta) */
		//Enter all values of matrix
		Vector3f o = new Vector3f(ray.getOrigin());
		Vector3f dir = new Vector3f(ray.getDirection());
		float a = dir.x;
		float b = p0.x-p1.x;
		float c = p0.x-p2.x;
		float d = dir.y;
		float e = p0.y-p1.y;
		float f = p0.y-p2.y;
		float g = dir.z;
		float h = p0.z-p1.z;
		float i = p0.z-p2.z;
		float ansx = p0.x-o.x;
		float ansy = p0.y-o.y;
		float ansz = p0.z-o.z;
		
		float deno = determinant(a, b, c, d, e, f, g, h, i);
		float t = determinant(ansx, b, c, ansy, e, f , ansz, h, i)/deno;
		float beta = determinant(a, ansx, c, d, ansy, f , g, ansz, i)/deno;
		float gamma = determinant(a, b, ansx, d, e, ansy , g, h, ansz)/deno;
		
		if (tmin>t || t>tmax || beta<0 || beta>1 || gamma<0 || gamma>1 || 1-gamma-beta<0) return null;
		Vector3f reverse_dir = new Vector3f(-dir.x, -dir.y, -dir.z);
		float angle = reverse_dir.dot(n0)/reverse_dir.length()/n0.length();
		Vector3f norm = new Vector3f(n0);
		if (angle<0) norm.scale(-1);
		HitRecord rec = new HitRecord();
		rec.pos = ray.pointAt(t);		// position of hit point
		rec.t = t;						// parameter t (distance along the ray)
		rec.material = material;		// material
		rec.normal = norm;					// normal at the hit point
		rec.normal.normalize();			// normal should be normalized
		return rec;
	}
}
