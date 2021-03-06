
package org.drip.learning.rxtor1;

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
 * <i>L1LossLearner</i> implements the Learner Class that holds the Space of Normed R<sup>x</sup> To Normed
 * R<sup>1</sup> Learning Functions that employs L<sub>1</sub> Empirical Loss Routine. Class-Specific
 * Asymptotic Sample, Covering Number based Upper Probability Bounds and other Parameters are also
 * maintained.
 *  
 * <br><br>
 * The References are:
 *  
 * <br><br>
 * <ul>
 * 	<li>
 *  	Alon, N., S. Ben-David, N. Cesa Bianchi, and D. Haussler (1997): Scale-sensitive Dimensions, Uniform
 *  		Convergence, and Learnability <i>Journal of Association of Computational Machinery</i> <b>44
 *  		(4)</b> 615-631
 * 	</li>
 * 	<li>
 *  	Anthony, M., and P. L. Bartlett (1999): <i>Artificial Neural Network Learning - Theoretical
 *  		Foundations</i> <b>Cambridge University Press</b> Cambridge, UK
 * 	</li>
 * 	<li>
 *  	Kearns, M. J., R. E. Schapire, and L. M. Sellie (1994): <i>Towards Efficient Agnostic Learning</i>
 *  		Machine Learning <b>17 (2)</b> 115-141
 * 	</li>
 * 	<li>
 *  	Lee, W. S., P. L. Bartlett, and R. C. Williamson (1998): The Importance of Convexity in Learning with
 *  		Squared Loss <i>IEEE Transactions on Information Theory</i> <b>44</b> 1974-1980
 * 	</li>
 * 	<li>
 *  	Vapnik, V. N. (1998): <i>Statistical learning Theory</i> <b>Wiley</b> New York
 * 	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning">Learning</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/rxtor1">R<sup>x</sup> To R<sup>1</sup></a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class L1LossLearner extends org.drip.learning.rxtor1.GeneralizedLearner {
	private org.drip.learning.bound.MeasureConcentrationExpectationBound _cleb = null;

	/**
	 * L1LossLearner Constructor
	 * 
	 * @param funcClassRxToR1 R^x To R^1 Function Class
	 * @param cdpb The Covering Number based Deviation Upper Probability Bound Generator
	 * @param regularizerFunc The Regularizer Function
	 * @param cleb The Concentration of Measure based Loss Expectation Upper Bound Evaluator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public L1LossLearner (
		final org.drip.spaces.functionclass.NormedRxToNormedR1Finite funcClassRxToR1,
		final org.drip.learning.bound.CoveringNumberLossBound cdpb,
		final org.drip.learning.regularization.RegularizationFunction regularizerFunc,
		final org.drip.learning.bound.MeasureConcentrationExpectationBound cleb)
		throws java.lang.Exception
	{
		super (funcClassRxToR1, cdpb, regularizerFunc);

		if (null == (_cleb = cleb)) throw new java.lang.Exception ("L1LossLearner ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Concentration of Measure based Loss Expectation Upper Bound Evaluator Instance
	 * 
	 * @return The Concentration of Measure based Loss Expectation Upper Bound Evaluator Instance
	 */

	public org.drip.learning.bound.MeasureConcentrationExpectationBound concentrationLossBoundEvaluator()
	{
		return _cleb;
	}

	@Override public double lossSampleCoveringNumber (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi,
		final double dblEpsilon,
		final boolean bSupremum)
		throws java.lang.Exception
	{
		org.drip.spaces.functionclass.NormedRxToNormedR1Finite funcClassRxToR1 = functionClass();

		return bSupremum ? funcClassRxToR1.sampleSupremumCoveringNumber (gvvi, dblEpsilon) :
			funcClassRxToR1.sampleCoveringNumber (gvvi, dblEpsilon);
	}

	@Override public double empiricalLoss (
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception
	{
		if (null == funcLearnerR1ToR1 || null == gvviX || !(gvviX instanceof
			org.drip.spaces.instance.ValidatedR1) || null == gvviY || !(gvviY instanceof
				org.drip.spaces.instance.ValidatedR1))
			throw new java.lang.Exception ("L1LossLearner::empiricalLoss => Invalid Inputs");

		double[] adblX = ((org.drip.spaces.instance.ValidatedR1) gvviX).instance();

		double[] adblY = ((org.drip.spaces.instance.ValidatedR1) gvviY).instance();

		double dblEmpiricalLoss = 0.;
		int iNumSample = adblX.length;

		if (iNumSample != adblY.length)
			throw new java.lang.Exception ("L1LossLearner::empiricalLoss => Invalid Inputs");

		for (int i = 0; i < iNumSample; ++i)
			dblEmpiricalLoss += java.lang.Math.abs (funcLearnerR1ToR1.evaluate (adblX[i]) - adblY[i]);

		return dblEmpiricalLoss;
	}

	@Override public double empiricalLoss (
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception
	{
		if (null == funcLearnerRdToR1 || null == gvviX || !(gvviX instanceof
			org.drip.spaces.instance.ValidatedRd) || null == gvviY || !(gvviY instanceof
				org.drip.spaces.instance.ValidatedR1))
			throw new java.lang.Exception ("L1LossLearner::empiricalLoss => Invalid Inputs");

		double[][] aadblX = ((org.drip.spaces.instance.ValidatedRd) gvviX).instance();

		double[] adblY = ((org.drip.spaces.instance.ValidatedR1) gvviY).instance();

		double dblEmpiricalLoss = 0.;
		int iNumSample = aadblX.length;

		if (iNumSample != adblY.length)
			throw new java.lang.Exception ("L1LossLearner::empiricalLoss => Invalid Inputs");

		for (int i = 0; i < iNumSample; ++i)
			dblEmpiricalLoss += java.lang.Math.abs (funcLearnerRdToR1.evaluate (aadblX[i]) - adblY[i]);

		return dblEmpiricalLoss;
	}

	@Override public double empiricalRisk (
		final org.drip.measure.continuous.R1R1 distR1R1,
		final org.drip.function.definition.R1ToR1 funcLearnerR1ToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception
	{
		if (null == distR1R1 || null == funcLearnerR1ToR1 || null == gvviX || !(gvviX instanceof
			org.drip.spaces.instance.ValidatedR1) || null == gvviY || !(gvviY instanceof
				org.drip.spaces.instance.ValidatedR1))
			throw new java.lang.Exception ("L1LossLearner::empiricalRisk => Invalid Inputs");

		double[] adblX = ((org.drip.spaces.instance.ValidatedR1) gvviX).instance();

		double[] adblY = ((org.drip.spaces.instance.ValidatedR1) gvviY).instance();

		double dblNormalizer = 0.;
		double dblEmpiricalLoss = 0.;
		int iNumSample = adblX.length;

		if (iNumSample != adblY.length)
			throw new java.lang.Exception ("L1LossLearner::empiricalRisk => Invalid Inputs");

		for (int i = 0; i < iNumSample; ++i) {
			double dblDensity = distR1R1.density (adblX[i], adblY[i]);

			dblNormalizer += dblDensity;

			dblEmpiricalLoss += dblDensity * java.lang.Math.abs (funcLearnerR1ToR1.evaluate (adblX[i]) -
				adblY[i]);
		}

		return dblEmpiricalLoss / dblNormalizer;
	}

	@Override public double empiricalRisk (
		final org.drip.measure.continuous.RdR1 distRdR1,
		final org.drip.function.definition.RdToR1 funcLearnerRdToR1,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviX,
		final org.drip.spaces.instance.GeneralizedValidatedVector gvviY)
		throws java.lang.Exception
	{
		if (null == distRdR1 || null == funcLearnerRdToR1 || null == gvviX || !(gvviX instanceof
			org.drip.spaces.instance.ValidatedRd) || null == gvviY || !(gvviY instanceof
				org.drip.spaces.instance.ValidatedR1))
			throw new java.lang.Exception ("L1LossLearner::empiricalRisk => Invalid Inputs");

		double[][] aadblX = ((org.drip.spaces.instance.ValidatedRd) gvviX).instance();

		double[] adblY = ((org.drip.spaces.instance.ValidatedR1) gvviY).instance();

		double dblNormalizer = 0.;
		double dblEmpiricalLoss = 0.;
		int iNumSample = aadblX.length;

		if (iNumSample != adblY.length)
			throw new java.lang.Exception ("L1LossLearner::empiricalRisk => Invalid Inputs");

		for (int i = 0; i < iNumSample; ++i) {
			double dblDensity = distRdR1.density (aadblX[i], adblY[i]);

			dblNormalizer += dblDensity;

			dblEmpiricalLoss += dblDensity * java.lang.Math.abs (funcLearnerRdToR1.evaluate (aadblX[i]) -
				adblY[i]);
		}

		return dblEmpiricalLoss / dblNormalizer;
	}
}
