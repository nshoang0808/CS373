// Sphere class
// defines a Sphere shape

import javax.vecmath.Vector3f;

public class Sphere extends Shape {
	private Vector3f center;	// center of sphere
	private float radius;		// radius of sphere

	public Sphere() {
	}
	public Sphere(Vector3f pos, float r, Material mat) {
		center = new Vector3f(pos);
		radius = r;
		material = mat;
	}
	public HitRecord hit(Ray ray, float tmin, float tmax) {

		/* YOUR WORK HERE: complete the sphere's intersection routine */
		/* compute ray-sphere intersection */
		Vector3f o = new Vector3f(ray.getOrigin());
		Vector3f d = new Vector3f(ray.getDirection());
		o.sub(center);
		float b = o.dot(d);
		float a = d.length()*d.length();
		float c = o.length()*o.length()-radius*radius;
		
		//If delta<0 => no solution => no intersection
		if (b*b-a*c<0) return null;
		float x1 = (float)((-b-Math.sqrt(b*b-a*c))/a);
		float x2 = (float)((-b+Math.sqrt(b*b-a*c))/a);
		float t = 0;
		if (tmin<=x1 && x1<=tmax) {
			if (tmin<=x2 && x2<=tmax && x2<x1) t=x2;
			else t = x1;
		} else if (tmin<=x2 && x2<=tmax) t = x2;
		else return null;
		
		HitRecord rec = new HitRecord();
		
		rec.pos = ray.pointAt(t);		// position of hit point
		Vector3f N = new Vector3f(rec.pos);
		N.sub(center);
		rec.t = t;						// parameter t (distance along the ray)
		rec.material = material;		// material
		rec.normal = N;					// normal at the hit point
		rec.normal.normalize();			// normal should be normalized
		
		return rec;
	}
}
