/*
  JWildfire - an image and animation processor written in Java 
  Copyright (C) 1995-2011 Andreas Maschke

  This is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser 
  General Public License as published by the Free Software Foundation; either version 2.1 of the 
  License, or (at your option) any later version.
 
  This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
  even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License along with this software; 
  if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
  02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/
package org.jwildfire.create.tina.variation;

import static org.jwildfire.base.mathlib.MathLib.sin;
import static org.jwildfire.base.mathlib.MathLib.cos;
import static org.jwildfire.base.mathlib.MathLib.EPSILON;
import static org.jwildfire.base.mathlib.MathLib.M_PI;
import static org.jwildfire.base.mathlib.MathLib.M_2PI;

import org.jwildfire.base.Tools;
import org.jwildfire.create.tina.base.XForm;
import org.jwildfire.create.tina.base.XYZPoint;

public class ShredradFunc extends VariationFunc {
  private static final long serialVersionUID = 1L;
  
  private static final String PARAM_N = "n";
  private static final String PARAM_WIDTH = "width";
  
  private static final String[] paramNames = { PARAM_N, PARAM_WIDTH };
  
  private double n = 4.0;
  private double width = 0.5;
  private double alpha;

  @Override
  public void transform(FlameTransformationContext pContext, XForm pXForm, XYZPoint pAffineTP, XYZPoint pVarTP, double pAmount) {
    /* shredrad by zy0rg */
	  
	double sa = alpha;
	double sw = width;
	
	double ang = pAffineTP.getPrecalcAtanYX();
	double rad = pAffineTP.getPrecalcSqrt();
	double xang = (ang + 3*M_PI + sa / 2) / sa;
	double zang = ((xang - (int)xang) * sw + (int)xang) * sa - M_PI - sa / 2 * sw;
	double s = sin(zang);
	double c = cos(zang);
	
    pVarTP.x += pAmount * rad * c;
    pVarTP.y += pAmount * rad * s;

    if (pContext.isPreserveZCoordinate()) pVarTP.z += pAmount * pAffineTP.z;

  }

  @Override
  public String[] getParameterNames() {
    return paramNames;
  }

  @Override
  public Object[] getParameterValues() {
    return new Object[] { n, width };
  }

  @Override
  public void setParameter(String pName, double pValue) {
    if (PARAM_N.equalsIgnoreCase(pName)) {
      n = (pValue==0) ? EPSILON : pValue;
      alpha = M_2PI / n;
    }
    else if (PARAM_WIDTH.equalsIgnoreCase(pName))
      width = Tools.limitValue(pValue, -1.0, 1.0);
    else
      throw new IllegalArgumentException(pName);
  }

  @Override
  public String getName() {
    return "shredrad";
  }

}
