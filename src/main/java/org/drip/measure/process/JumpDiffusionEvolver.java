
package org.drip.measure.process;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
 */

/**
 * <i>JumpDiffusionEvolver</i> implements the Functionality that guides the Single Factor R<sup>1</sup> Jump
 * Diffusion Random Process Variable Evolution.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/process">Process</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class JumpDiffusionEvolver extends org.drip.measure.process.DiffusionEvolver {
	private org.drip.measure.dynamics.HazardJumpEvaluator _heie = null;

	/**
	 * JumpDiffusionEvolver Constructor
	 * 
	 * @param de The Diffusion Evaluator Instance
	 * @param heie The Hazard Point Event Indicator Function Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public JumpDiffusionEvolver (
		final org.drip.measure.dynamics.DiffusionEvaluator de,
		final org.drip.measure.dynamics.HazardJumpEvaluator heie)
		throws java.lang.Exception
	{
		super (de);

		if (null == (_heie = heie))
			throw new java.lang.Exception ("JumpDiffusionEvolver Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Hazard Point Event Indicator Instance
	 * 
	 * @return The Hazard Point Event Indicator Instance
	 */

	public org.drip.measure.dynamics.HazardJumpEvaluator eventIndicationEvaluator()
	{
		return _heie;
	}

	@Override public org.drip.measure.realization.JumpDiffusionEdge increment (
		final org.drip.measure.realization.JumpDiffusionVertex jdv,
		final org.drip.measure.realization.JumpDiffusionEdgeUnit ur,
		final double dblTimeIncrement)
	{
		if (null == jdv || null == ur || !org.drip.numerical.common.NumberUtil.IsValid (dblTimeIncrement))
			return null;

		double dblPreviousValue = jdv.value();

		try {
			if (jdv.jumpOccurred())
				return org.drip.measure.realization.JumpDiffusionEdge.Standard (dblPreviousValue, 0., 0., new
					org.drip.measure.realization.StochasticEdgeJump (false, 0., 0., dblPreviousValue), ur);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		double dblHazardRate = _heie.hazardRate();

		org.drip.measure.dynamics.DiffusionEvaluator de = evaluator();

		double dblLevelHazardIntegral = dblHazardRate * dblTimeIncrement;

		boolean bEventOccurred = java.lang.Math.exp (-1. * (jdv.cumulativeHazardIntegral() +
			dblLevelHazardIntegral)) <= ur.jump();

		try {
			org.drip.measure.realization.StochasticEdgeJump sej = new
				org.drip.measure.realization.StochasticEdgeJump (bEventOccurred, dblHazardRate,
					dblLevelHazardIntegral, _heie.magnitudeEvaluator().value (jdv));

			if (bEventOccurred)
				return org.drip.measure.realization.JumpDiffusionEdge.Standard (dblPreviousValue, 0., 0.,
					sej, ur);

			org.drip.measure.dynamics.LocalEvaluator leVolatility = de.volatility();

			return org.drip.measure.realization.JumpDiffusionEdge.Standard (dblPreviousValue,
				de.drift().value (jdv) * dblTimeIncrement, null == leVolatility ? 0. : leVolatility.value
					(jdv) * ur.diffusion() * java.lang.Math.sqrt (java.lang.Math.abs (dblTimeIncrement)),
						sej, ur);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
