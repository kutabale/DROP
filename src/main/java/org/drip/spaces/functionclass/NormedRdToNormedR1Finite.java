
package org.drip.spaces.functionclass;

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
 * <i>NormedRdToNormedR1Finite</i> implements the Class F of f : Normed R<sup>d</sup> To Normed R<sup>1</sup>
 * Spaces of Finite Functions. The Reference we've used is:
 *
 * <br><br>
 * 	<ul>
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/functionclass">Function Class</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NormedRdToNormedR1Finite extends org.drip.spaces.functionclass.NormedRxToNormedR1Finite {

	/**
	 * NormedRdToNormedR1Finite Function Class Constructor
	 * 
	 * @param dblMaureyConstant The Maurey Constant
	 * @param aNormedRdToNormedR1 Array of the Function Spaces
	 * 
	 * @throws java.lang.Exception Thrown if NormedRdToNormedR1Class Instance cannot be created
	 */

	public NormedRdToNormedR1Finite (
		final double dblMaureyConstant,
		final org.drip.spaces.rxtor1.NormedRdToNormedR1[] aNormedRdToNormedR1)
		throws java.lang.Exception
	{
		super (dblMaureyConstant, aNormedRdToNormedR1);

		for (int i = 0; i < aNormedRdToNormedR1.length; ++i) {
			if (null == aNormedRdToNormedR1[i])
				throw new java.lang.Exception ("NormedRdToNormedR1Finite ctr: Invalid Input Function");
		}
	}

	/**
	 * Retrieve the Finite Class of R^d To R^1 Functions
	 * 
	 * @return The Finite Class of R^d To R^1 Functions
	 */

	public org.drip.function.definition.RdToR1[] functionRdToR1Set()
	{
		org.drip.spaces.rxtor1.NormedRdToNormedR1[] aNormedRdToNormedR1 =
			(org.drip.spaces.rxtor1.NormedRdToNormedR1[]) functionSpaces();

		if (null == aNormedRdToNormedR1) return null;

		int iNumFunction = aNormedRdToNormedR1.length;
		org.drip.function.definition.RdToR1[] aRdToR1 = new
			org.drip.function.definition.RdToR1[iNumFunction];

		for (int i = 0; i < iNumFunction; ++i)
			aRdToR1[i] = aNormedRdToNormedR1[i].function();

		return aRdToR1;
	}
}
