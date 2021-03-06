
package org.drip.learning.bound;

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
 * <i>DiagonalOperatorCoveringBound</i> implements the Behavior of the Bound on the Covering Number of the
 * Diagonal Scaling Operator. The Asymptote is set as either
 * <br><br>
 * 				log [e_n(A)] ~ O {(1/log n)^alpha}
 * <br><br>
 * 		- OR -
 * <br><br>
 * 					  e_n(A) ~ O {(1/log n)^alpha}
 * <br><br>
 *  The References are:
 * <ul>
 * 	<li>
 *  	Ash, R. (1965): <i>Information Theory</i> <b>Inter-science</b> New York
 * 	</li>
 * 	<li>
 * 		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and Approximation of Operators</i>
 * 			<b>Cambridge University Press</b> Cambridge UK
 * 	</li>
 * 	<li>
 *  	Gordon, Y., H. Konig, and C. Schutt (1987): Geometric and Probabilistic Estimates of Entropy and
 *  		Approximation Numbers of Operators <i>Journal of Approximation Theory</i> <b>49</b> 219-237
 * 	</li>
 * 	<li>
 *  	Konig, H. (1986): <i>Eigenvalue Distribution of Compact Operators</i> <b>Birkhauser</b> Basel,
 *  		Switzerland
 * 	</li>
 * 	<li>
 *  	Smola, A. J., A. Elisseff, B. Scholkopf, and R. C. Williamson (2000): Entropy Numbers for Convex
 *  		Combinations and mlps, in: <i>Advances in Large Margin Classifiers, A. Smola, P. Bartlett, B.
 *  		Scholkopf, and D. Schuurmans - editors</i> <b>MIT Press</b> Cambridge, MA
 * 	</li>
 * 	<li>
 * 		Williamson, R. C., A. J. Smola, and B. Scholkopf (2001): Generalization Performance of Regularization
 *  		Networks and Support Vector Machines via Entropy Numbers of Compact Operators <i>IEEE
 *  		Transactions on Information Theory</i> <b>47 (6)</b> 2516-2532
 * 	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning">Learning</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/bound">Bound</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DiagonalOperatorCoveringBound {

	/**
	 * Asymptote on the Base Diagonal Operator Entropy Number
	 */

	public static final int BASE_DIAGONAL_ENTROPY_ASYMPTOTE_EXPONENT = 1;

	/**
	 * Asymptote on the Log of the Diagonal Operator Entropy Number
	 */

	public static final int LOG_DIAGONAL_ENTROPY_ASYMPTOTE_EXPONENT = 2;

	private double _dblOperatorEntropyAsymptoteExponent = java.lang.Double.NaN;
	private int _iOperatorEntropyAsymptoteBase = BASE_DIAGONAL_ENTROPY_ASYMPTOTE_EXPONENT;

	/**
	 * DiagonalOperatorCoveringBound Constructor
	 * 
	 * @param iOperatorEntropyAsymptoteBase Indicate the Asymptote is on the Base Value or Log Value
	 * @param dblOperatorEntropyAsymptoteExponent The Entropy Number Asymptote Exponent
	 * 
	 * @throws java.lang.Exception Throws if the Inputs are Invalid
	 */

	public DiagonalOperatorCoveringBound (
		final int iOperatorEntropyAsymptoteBase,
		final double dblOperatorEntropyAsymptoteExponent)
		throws java.lang.Exception
	{
		if ((BASE_DIAGONAL_ENTROPY_ASYMPTOTE_EXPONENT != (_iOperatorEntropyAsymptoteBase =
			iOperatorEntropyAsymptoteBase) && LOG_DIAGONAL_ENTROPY_ASYMPTOTE_EXPONENT !=
				_iOperatorEntropyAsymptoteBase) || !org.drip.numerical.common.NumberUtil.IsValid
					(_dblOperatorEntropyAsymptoteExponent = dblOperatorEntropyAsymptoteExponent))
			throw new java.lang.Exception ("DiagonalOperatorCoveringBound ctr => Invalid Inputs");
	}

	/**
	 * Retrieve the Entropy Number Asymptote Type
	 * 
	 * @return The Entropy Number Asymptote Type
	 */

	public int entropyNumberAsymptoteType()
	{
		return _iOperatorEntropyAsymptoteBase;
	}

	/**
	 * Retrieve the Entropy Number Asymptote Exponent
	 * 
	 * @return The Entropy Number Asymptote Exponent
	 */

	public double entropyNumberAsymptoteExponent()
	{
		return _dblOperatorEntropyAsymptoteExponent;
	}
}
