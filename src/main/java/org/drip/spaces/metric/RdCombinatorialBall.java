
package org.drip.spaces.metric;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>RdCombinatorialBall</i> extends the Combinatorial R<sup>d</sup> Banach Space by enforcing the Closed
 * Bounded Metric. The Reference we've used is:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and the Approximation of Operators</i>
 *  			<b>Cambridge University Press</b> Cambridge UK 
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces">Spaces</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/metric">Metric</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RdCombinatorialBall extends org.drip.spaces.metric.RdCombinatorialBanach {
	private double _dblNormRadius = java.lang.Double.NaN;

	/**
	 * Construct a RdCombinatorialBall Instance of Unit Radius
	 * 
	 * @param aR1CV Array of Combinatorial R^d Vector Spaces
	 * @param distRd The R^d Borel Sigma Measure
	 * @param iPNorm The p-norm of the Space
	 * 
	 * @return RdCombinatorialBall Instance of Unit Radius
	 */

	public static final RdCombinatorialBall ClosedUnit (
		final org.drip.spaces.tensor.R1CombinatorialVector[] aR1CV,
		final org.drip.measure.continuous.Rd distRd,
		final int iPNorm)
	{
		try {
			return new RdCombinatorialBall (aR1CV, distRd, iPNorm, 1.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * RdCombinatorialBall Constructor
	 * 
	 * @param aR1CV Array of Combinatorial R^d Vector Spaces
	 * @param distRd The R^d Borel Sigma Measure
	 * @param iPNorm The p-norm of the Space
	 * @param dblNormRadius Radius Norm of the Unit Ball
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RdCombinatorialBall (
		final org.drip.spaces.tensor.R1CombinatorialVector[] aR1CV,
		final org.drip.measure.continuous.Rd distRd,
		final int iPNorm,
		final double dblNormRadius)
		throws java.lang.Exception
	{
		super (aR1CV, distRd, iPNorm);

		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblNormRadius = dblNormRadius) || 0. >=
			_dblNormRadius)
			throw new java.lang.Exception ("RdCombinatorialBall Constructor: Invalid Inputs");
	}

	/**
	 * Retrieve the Radius Norm
	 * 
	 * @return The Radius Norm
	 */

	public double normRadius()
	{
		return _dblNormRadius;
	}

	@Override public boolean validateInstance (
		final double[] adblInstance)
	{
		try {
			return super.validateInstance (adblInstance) && _dblNormRadius <= sampleMetricNorm
				(adblInstance);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
