package util;

public class PVector {
	public float x, y, z;
	
	public PVector(double x, double y){
		this.x = (float) x;
		this.y = (float) y;
		this.z = 0;
	}
	
	public PVector(double x, double y, double z){
		this.x = (float) x;
		this.y = (float) y;
		this.z = (float) z;
	}
	
	public String toString() {
		return "[ " + x + ", " + y + ", " + z + " ]";
	}
	public boolean equals(PVector v) {
		return (x == v.x) && ( y == v.y) && (z == v.z);
	}
	
	public PVector set(PVector p) {
		x = p.x;
		y = p.y;
		z = p.z;
		return this;
	}
	
	public PVector set(float[] p) {
		switch(p.length) {
		default:
		case 3:
			z = p[2];
		case 2:
			y = p[1];
		case 1:
			x = p[0];
		case 0:
			break;
		}
		return this;
	}
	
	public PVector set(double x, double y) {
		this.x = (float)x;
		this.y = (float)y;
		return this;
	}
	
	public PVector set(double x, double y, double z) {
		this.x = (float)x;
		this.y = (float)y;
		this.z = (float)z;
		return this;
	}
	
	public static PVector random2D() {
		return new PVector(Util.random(), Util.random());
	}
	
	public static PVector random3D() {
		return new PVector(Util.random(), Util.random(), Util.random());
	}
	
	public static PVector fromAngle(double angle) {
		return new PVector(Math.cos(angle),Math.sin(angle));
	}
	
	public PVector copy() {
		return new PVector(x, y, z);
	}
	
	public float mag() {
		return (float)Math.sqrt(
				Math.pow(x, 2) +
				Math.pow(y, 2) +
				Math.pow(z, 2) 
				);
	}
	
	public float magSq() {
		return (float)( Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
	
	public PVector add(PVector v) {
		x += v.x;
		y += v.y;
		z += v.z;
		return this;
	}
	
	public PVector add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public PVector add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	public static PVector add(PVector v1, PVector v2) {
		return new PVector(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
	}
	
	public static PVector add(PVector v1, PVector v2, PVector target) {
		if(target == null) 
			target = PVector.add(v1,  v2);
		else
			target.set(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
		return target;
	}
	
	public PVector sub(PVector v) {
		x -= v.x;
		y -= v.y;
		z -= v.z;
		return this;
	}
	
	public PVector sub(double x, double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public PVector sub(double x, double y, double z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}
	
	public static PVector sub(PVector v1, PVector v2) {
		return new PVector(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
	}
	
	public static PVector sub(PVector v1, PVector v2, PVector target) {
		if(target == null) 
			target = PVector.add(v1,  v2);
		else
			target.set(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
		return target;
	}
	
	public PVector mult(double n) {
		x *= n;
		y *= n;
		z *= n;
		return this;
	}
	
	public static PVector mult(PVector v, double n) {
		v.x *= n;
		v.y *= n;
		v.z *= n;
		return v;
	}
	public static PVector mult(PVector v, double n, PVector target) {
		target.x = v.x * (float)n;
		target.y = v.y * (float)n;
		target.z = v.z * (float)n;
		return target;
	}
	
	public PVector div(double n) {
		x /= n;
		y /= n;
		z /= n;
		return this;
	}
	
	public static PVector div(PVector v, double n) {
		v.x /= n;
		v.y /= n;
		v.z /= n;
		return v;
	}
	public static PVector div(PVector v, double n, PVector target) {
		target.x = v.x / (float)n;
		target.y = v.y / (float)n;
		target.z = v.z / (float)n;
		return target;
	}
	
	public float dist(PVector v) {
		return (float) Math.sqrt( Math.pow(x - v.x, 2) + Math.pow(y - v.y, 2) + Math.pow(z - v.z, 2));
	}
	
	public static float dist(PVector v1, PVector v2) {
		return (float) Math.sqrt( Math.pow(v1.x - v2.x, 2) + Math.pow(v1.y - v2.y, 2) + Math.pow(v1.z - v2.z, 2));
	}
	
	public float dot(PVector v) {
		return x * v.x + y*v.y + z*v.z; 
	}
	public float dot(double x, double y, double z) {
		return (float)(x * this.x + y*this.y + z*this.z); 
	}
	
	public static float dot(PVector v1, PVector v2) {
		return v1.x * v2.x + v1.y*v2.y + v1.z*v2.z; 
	}
	
	public PVector cross(PVector v) {
		return this.set(y*v.z - z*v.y,z*v.x - x*v.z,x*v.y - y*v.x);
	}
	
	public PVector cross(PVector v, PVector target) {
		return target.set(y*v.z - z*v.y,z*v.x - x*v.z,x*v.y - y*v.x);
	}
	public static PVector cross(PVector v1, PVector v2, PVector target) {
		return target.set(v1.y*v2.z - v1.z*v2.y,v1.z*v2.x - v1.x*v2.z,v1.x*v2.y - v1.y*v2.x);
	}
	
	public PVector normalize() {
		float len = mag();
		x /= len;
		y /= len;
		z /= len;
		return this;
	}
	
	public static PVector normalize(PVector target) {
		float len = target.mag();
		target.x /= len;
		target.y /= len;
		target.z /= len;
		return target;
	}
	
	public PVector setMag(double len) {
		normalize();
		mult(len);
		return this;
	}
	
	public PVector setMag(PVector target, double len) {
		if(target == null)
			target = PVector.normalize(this).setMag(len);
		else {
			target.set(this);
			target.setMag(len);
		}
		return target;
	} 
	
	public PVector limit(double max) {
		if(magSq() > max * max)
			setMag(max);
		return this;
	}

	
	public float heading() {
		return (float) Math.atan(y/x);
	}
	
	public PVector rotate(double angle) {
		set(PVector.fromAngle(this.heading()+ angle));
		return this;
	}
	
	public PVector lerp(PVector v,double amt) {
		x += (v.x - x) * amt;
		y += (v.y - y) * amt;
		z += (v.z - z) * amt;
		return this;
	}
	public PVector lerp(double x, double y, double z,double amt) {
		this.x += (x - this.x) * amt;
		this.y += (y - this.y) * amt;
		this.z += (z - this.z) * amt;
		return this;
	}
	
	public static PVector lerp(PVector v1, PVector v2, double amt) {
		return new PVector(
				v1.x + (v2.x - v1.x) * amt,
				v1.y + (v2.y - v1.y) * amt,
				v1.z + (v2.z - v1.z) * amt);
	}
	
	public static float angleBetween(PVector v1, PVector v2) {
		float theta1 =  v1.heading(), theta2 = v2.heading();
		return (theta1 > theta2 ? theta1 - theta2 : theta2 - theta1);
	}
	
	public float[] array() {
		float[] tmp = {x,y,z};
		return tmp;
	}
}
