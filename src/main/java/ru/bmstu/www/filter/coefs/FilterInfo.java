/*package ru.bmstu.www.filter.coefs;

public final class FilterInfo {

	public final static short COUNT_OF_COEFS = 101;

	public final static double[][] CoefsOfBands = {
			// 0 band
			{ 0.0004205876587575, 0.000462063390119, 0.0005159005551854, 0.0005845026051592, 0.0006702347918949,
					0.0007753969572331, 0.0009021964293526, 0.001052721310638, 0.00122891444088, 0.001432548315935,
					0.001665201235257, 0.001928234942074, 0.002222774007442, 0.002549687194119, 0.002909571018296,
					0.003302735706846, 0.003729193725174, 0.004188651026069, 0.004680501143619, 0.005203822228266,
					0.005757377089991, 0.006339616286541, 0.006948684262946, 0.007582428517627, 0.008238411739488,
					0.008913926829805, 0.00960601469291, 0.01031148465076, 0.01102693730896, 0.01174878967593,
					0.01247330231274, 0.01319660826936, 0.01391474354353, 0.01462367878158, 0.01531935192642,
					0.01599770150667, 0.01665470025306, 0.01728638872314, 0.01788890861365, 0.01845853544171,
					0.01899171028066, 0.01948507024444, 0.01993547742577, 0.02034004600755, 0.02069616728387,
					0.0210015323471, 0.02125415221983, 0.0214523752349, 0.02159490149364, 0.02168079426081,
					0.02170948818455, 0.02168079426081, 0.02159490149364, 0.0214523752349, 0.02125415221983,
					0.0210015323471, 0.02069616728387, 0.02034004600755, 0.01993547742577, 0.01948507024444,
					0.01899171028066, 0.01845853544171, 0.01788890861365, 0.01728638872314, 0.01665470025306,
					0.01599770150667, 0.01531935192642, 0.01462367878158, 0.01391474354353, 0.01319660826936,
					0.01247330231274, 0.01174878967593, 0.01102693730896, 0.01031148465076, 0.00960601469291,
					0.008913926829805, 0.008238411739488, 0.007582428517627, 0.006948684262946, 0.006339616286541,
					0.005757377089991, 0.005203822228266, 0.004680501143619, 0.004188651026069, 0.003729193725174,
					0.003302735706846, 0.002909571018296, 0.002549687194119, 0.002222774007442, 0.001928234942074,
					0.001665201235257, 0.001432548315935, 0.00122891444088, 0.001052721310638, 0.0009021964293526,
					0.0007753969572331, 0.0006702347918949, 0.0005845026051592, 0.0005159005551854, 0.000462063390119,
					0.0004205876587575 },

			// 1 band
			{ -6.91045199976e-05, -0.0001611010971019, -0.0002734370380816, -0.0004131495225181, -0.0005881995441097,
					-0.0008070669576381, -0.001078273206328, -0.001409849177959, -0.001808768818081, -0.002280371582847,
					-0.002827798424654, -0.003451466683302, -0.004148608950084, -0.004912899665342, -0.00573419092271,
					-0.006598375744793, -0.007487393061244, -0.008379383890311, -0.009249002957758, -0.01006788436501,
					-0.01080525414161, -0.0114286767958, -0.01190491752451, -0.01220089676738, -0.0122847094845,
					-0.01212667807694, -0.01170040540155, -0.01098379296969, -0.00995998923945, -0.008618233948819,
					-0.00695456668279, -0.00497237127055, -0.002682732074834, -0.0001045836310289, 0.002735358749942,
					0.005802893081594, 0.009056848110299, 0.0124498154711, 0.0159290810008, 0.01943773296857,
					0.02291591894493, 0.02630221781444, 0.02953508923176, 0.03255435977902, 0.03530270331917,
					0.03772707262775, 0.03978004034932, 0.04142100964526, 0.04261725850699, 0.04334478649372,
					0.04358893846145, 0.04334478649372, 0.04261725850699, 0.04142100964526, 0.03978004034932,
					0.03772707262775, 0.03530270331917, 0.03255435977902, 0.02953508923176, 0.02630221781444,
					0.02291591894493, 0.01943773296857, 0.0159290810008, 0.0124498154711, 0.009056848110299,
					0.005802893081594, 0.002735358749942, -0.0001045836310289, -0.002682732074834, -0.00497237127055,
					-0.00695456668279, -0.008618233948819, -0.00995998923945, -0.01098379296969, -0.01170040540155,
					-0.01212667807694, -0.0122847094845, -0.01220089676738, -0.01190491752451, -0.0114286767958,
					-0.01080525414161, -0.01006788436501, -0.009249002957758, -0.008379383890311, -0.007487393061244,
					-0.006598375744793, -0.00573419092271, -0.004912899665342, -0.004148608950084, -0.003451466683302,
					-0.002827798424654, -0.002280371582847, -0.001808768818081, -0.001409849177959, -0.001078273206328,
					-0.0008070669576381, -0.0005881995441097, -0.0004131495225181, -0.0002734370380816,
					-0.0001611010971019, -6.91045199976e-05 },
			// 2 band
			{ -0.0002257045476287, 0, 0.0002302983130747, 0.0004613391610798, 0.0006853480757348, 0.0008896559395359,
					0.001057133902711, 0.001168230369713, 0.001204495371683, 0.001153152228604, 0.001011990327236,
					0.0007936584160409, 0.0005283771819355, 0.0002641878362787, 6.411246148304e-05, -4.819471808808e-19,
					0.0001433239718111, 0.0005537209229798, 0.001266538223085, 0.002281021181098, 0.003550947121271,
					0.004979462070904, 0.006419575407284, 0.007681232580565, 0.008545162020787, 0.008782854851014,
					0.008181181824666, 0.00656938820467, 0.003845638739883, 1.169744008468e-18, -0.004869193747576,
					-0.01054821983275, -0.01671194969776, -0.02293974018187, -0.02874280645193, -0.03360136609407,
					-0.03700865317055, -0.03851794386975, -0.03778813343583, -0.03462324472148, -0.02900157331717,
					-0.02109097142144, -0.01124797764204, -6.83702731104e-18, 0.01198859495622, 0.02396401330226,
					0.03514028856653, 0.04476129432526, 0.05216195414102, 0.05682304101991, 0.05841447287652,
					0.05682304101991, 0.05216195414102, 0.04476129432526, 0.03514028856653, 0.02396401330226,
					0.01198859495622, -6.83702731104e-18, -0.01124797764204, -0.02109097142144, -0.02900157331717,
					-0.03462324472148, -0.03778813343583, -0.03851794386975, -0.03700865317055, -0.03360136609407,
					-0.02874280645193, -0.02293974018187, -0.01671194969776, -0.01054821983275, -0.004869193747576,
					1.169744008468e-18, 0.003845638739883, 0.00656938820467, 0.008181181824666, 0.008782854851014,
					0.008545162020787, 0.007681232580565, 0.006419575407284, 0.004979462070904, 0.003550947121271,
					0.002281021181098, 0.001266538223085, 0.0005537209229798, 0.0001433239718111, -4.819471808808e-19,
					6.411246148304e-05, 0.0002641878362787, 0.0005283771819355, 0.0007936584160409, 0.001011990327236,
					0.001153152228604, 0.001204495371683, 0.001168230369713, 0.001057133902711, 0.0008896559395359,
					0.0006853480757348, 0.0004613391610798, 0.0002302983130747, 0, -0.0002257045476287 },

			// 3 band
			{ -0.0007910354533621, -0.0006349159612884, -0.0003435779007049, -7.001530416051e-05, 2.981228150433e-05,
					-0.0001332197142784, -0.0005154745063702, -0.0009168129827752, -0.001034603187843,
					-0.0005988443399171, 0.0004598426528031, 0.001898900594281, 0.003196252610888, 0.00375324667646,
					0.003198042704983, 0.001637642849333, -0.0003035369602343, -0.001734198025884, -0.001957291698134,
					-0.0009191959225382, 0.0006203705471522, 0.001361642444533, 0.0001230192028078, -0.003409890481639,
					-0.008253757660853, -0.01232961574742, -0.01331952862306, -0.009839163375412, -0.002347832197971,
					0.006746532101044, 0.01399032286147, 0.01648868661364, 0.01347426809334, 0.006995733283199,
					0.001206582710051, 0.0003879378564844, 0.006527864324401, 0.01765154446226, 0.02791315147977,
					0.02973526582197, 0.01730434493161, -0.01004750511302, -0.04621677885887, -0.07951144138279,
					-0.09653758929094, -0.08744413286163, -0.05042233518437, 0.006528646632554, 0.06755797746066,
					0.1141288243667, 0.131515945364, 0.1141288243667, 0.06755797746066, 0.006528646632554,
					-0.05042233518437, -0.08744413286163, -0.09653758929094, -0.07951144138279, -0.04621677885887,
					-0.01004750511302, 0.01730434493161, 0.02973526582197, 0.02791315147977, 0.01765154446226,
					0.006527864324401, 0.0003879378564844, 0.001206582710051, 0.006995733283199, 0.01347426809334,
					0.01648868661364, 0.01399032286147, 0.006746532101044, -0.002347832197971, -0.009839163375412,
					-0.01331952862306, -0.01232961574742, -0.008253757660853, -0.003409890481639, 0.0001230192028078,
					0.001361642444533, 0.0006203705471522, -0.0009191959225382, -0.001957291698134, -0.001734198025884,
					-0.0003035369602343, 0.001637642849333, 0.003198042704983, 0.00375324667646, 0.003196252610888,
					0.001898900594281, 0.0004598426528031, -0.0005988443399171, -0.001034603187843, -0.0009168129827752,
					-0.0005154745063702, -0.0001332197142784, 2.981228150433e-05, -7.001530416051e-05,
					-0.0003435779007049, -0.0006349159612884, -0.0007910354533621 },

			// 4 band
			{ 5.078930574039e-05, 0.0001539110525732, -4.067743723485e-19, -0.0001748195285385, -6.517851357683e-05,
					0.0004468518681464, 0.001107851924332, 0.001364183006715, 0.0007323534389246, -0.0007408489489167,
					-0.002309471448312, -0.002908651885998, -0.001925118351554, 0.0002102566392267, 0.002178217108671,
					0.002713970854598, 0.001665634007679, 0.0002333653697589, -5.363455561157e-18, 0.001403737244598,
					0.002979685729797, 0.002291743955286, -0.001884981338701, -0.007848800430448, -0.01144314264221,
					-0.008876789245504, 1.014613665889e-17, 0.01067882905492, 0.01656860986491, 0.01369083586054,
					0.003966840105108, -0.005829871202067, -0.009185266498666, -0.005259572597855, -1.361924272863e-17,
					-0.001306725969839, -0.01148286719481, -0.02316812088947, -0.02317786010273, -0.002810065893932,
					0.03260188126482, 0.06305959586313, 0.06488044918377, 0.02736831661441, -0.03623749742571,
					-0.09264728806577, -0.1069452860004, -0.06485128403525, 0.01581408777229, 0.09312828732531,
					0.1247799635397, 0.09312828732531, 0.01581408777229, -0.06485128403525, -0.1069452860004,
					-0.09264728806577, -0.03623749742571, 0.02736831661441, 0.06488044918377, 0.06305959586313,
					0.03260188126482, -0.002810065893932, -0.02317786010273, -0.02316812088947, -0.01148286719481,
					-0.001306725969839, -1.361924272863e-17, -0.005259572597855, -0.009185266498666, -0.005829871202067,
					0.003966840105108, 0.01369083586054, 0.01656860986491, 0.01067882905492, 1.014613665889e-17,
					-0.008876789245504, -0.01144314264221, -0.007848800430448, -0.001884981338701, 0.002291743955286,
					0.002979685729797, 0.001403737244598, -5.363455561157e-18, 0.0002333653697589, 0.001665634007679,
					0.002713970854598, 0.002178217108671, 0.0002102566392267, -0.001925118351554, -0.002908651885998,
					-0.002309471448312, -0.0007408489489167, 0.0007323534389246, 0.001364183006715, 0.001107851924332,
					0.0004468518681464, -6.517851357683e-05, -0.0001748195285385, -4.067743723485e-19,
					0.0001539110525732, 5.078930574039e-05 },

			// 5 band
			{ 0.0006354729158216, 0.0009144026618227, -4.33851694828e-18, -0.001038622240324, -0.0008155098690127,
					0.0002665377516434, 0.0006152499160911, 9.466241607358e-05, 0.000224455790147, 0.001092681751258,
					0.0004879255931918, -0.002060864554098, -0.003010163772367, 0.0001258635660644, 0.003717510917465,
					0.002761307795139, -0.0009794941126365, -0.001770855696209, 1.191578265683e-18, -0.001284475944864,
					-0.004591121252259, -0.001636698926036, 0.007547414868898, 0.009961722503474, -0.0008011692402756,
					-0.01132716240836, -0.007529871605078, 0.002741349916059, 0.003520443923566, -0.000914779029392,
					0.005048247644049, 0.01465437276149, 0.004213172708787, -0.02214015683741, -0.02704605186897,
					0.003219514311498, 0.02977136477428, 0.01811877522286, -0.006786925067528, -0.005060942853859,
					0.005000485216085, -0.01959922295414, -0.05115668532197, -0.01246821204457, 0.08356297865821,
					0.1049689150638, -0.01887714365707, -0.155382201448, -0.1159443094906, 0.07604610115468,
					0.1871227165483, 0.07604610115468, -0.1159443094906, -0.155382201448, -0.01887714365707,
					0.1049689150638, 0.08356297865821, -0.01246821204457, -0.05115668532197, -0.01959922295414,
					0.005000485216085, -0.005060942853859, -0.006786925067528, 0.01811877522286, 0.02977136477428,
					0.003219514311498, -0.02704605186897, -0.02214015683741, 0.004213172708787, 0.01465437276149,
					0.005048247644049, -0.000914779029392, 0.003520443923566, 0.002741349916059, -0.007529871605078,
					-0.01132716240836, -0.0008011692402756, 0.009961722503474, 0.007547414868898, -0.001636698926036,
					-0.004591121252259, -0.001284475944864, 1.191578265683e-18, -0.001770855696209, -0.0009794941126365,
					0.002761307795139, 0.003717510917465, 0.0001258635660644, -0.003010163772367, -0.002060864554098,
					0.0004879255931918, 0.001092681751258, 0.000224455790147, 9.466241607358e-05, 0.0006152499160911,
					0.0002665377516434, -0.0008155098690127, -0.001038622240324, -4.33851694828e-18, 0.0009144026618227,
					0.0006354729158216 },

			// 6 band
			{ -0.0001649894145874, 0.0009996232678831, -5.426080356815e-18, -0.001135419877168, 0.0002117328567886,
					0.0008791603752016, -0.0002376135947434, -0.0001581853639436, -0.0002245843244938,
					-0.0008565791495319, 0.001333800873291, 0.001692276355778, -0.002792250057898, -0.001814404482718,
					0.003719639745762, 0.001098868114726, -0.002990371874583, -0.0001745139406725, -2.511731428197e-18,
					0.0002162705224264, 0.004593750352497, -0.002093484733068, -0.008794183865744, 0.005327700766902,
					0.01019109260142, -0.007682386165444, -0.007534183577282, 0.006019319655448, 0.001961172890078,
					0.001712411238934, 0.003175278414492, -0.01440776520021, -0.004215585377231, 0.02712216686497,
					-3.252757218893e-17, -0.03270699627395, 0.006137127718617, 0.02537845396304, -0.006790811592262,
					-0.004468301438985, -0.006284835415844, -0.02389539810885, 0.0374707380964, 0.04858501793077,
					-0.08361083090061, -0.05830218164563, 0.133691127231, 0.04716166711788, -0.1725673980094,
					-0.01807114793769, 0.1872298721539, -0.01807114793769, -0.1725673980094, 0.04716166711788,
					0.133691127231, -0.05830218164563, -0.08361083090061, 0.04858501793077, 0.0374707380964,
					-0.02389539810885, -0.006284835415844, -0.004468301438985, -0.006790811592262, 0.02537845396304,
					0.006137127718617, -0.03270699627395, -3.252757218893e-17, 0.02712216686497, -0.004215585377231,
					-0.01440776520021, 0.003175278414492, 0.001712411238934, 0.001961172890078, 0.006019319655448,
					-0.007534183577282, -0.007682386165444, 0.01019109260142, 0.005327700766902, -0.008794183865744,
					-0.002093484733068, 0.004593750352497, 0.0002162705224264, -2.511731428197e-18, -0.0001745139406725,
					-0.002990371874583, 0.001098868114726, 0.003719639745762, -0.001814404482718, -0.002792250057898,
					0.001692276355778, 0.001333800873291, -0.0008565791495319, -0.0002245843244938, -0.0001581853639436,
					-0.0002376135947434, 0.0008791603752016, 0.0002117328567886, -0.001135419877168,
					-5.426080356815e-18, 0.0009996232678831, -0.0001649894145874 }

	};
}
 */

 
 package ru.bmstu.www.filter.coefs;

public final class FilterInfo {

	public final static short COUNT_OF_COEFS = 101;

	public final static double[][] CoefsOfBands = {
			// 0 band
			{ -1.078270155821e-06, 1.802213980442e-06, -3.460517485139e-06, 6.140702894681e-06, -1.02803043909e-05,
					1.64425971983e-05, -2.53366953695e-05, 3.783732282113e-05, -5.500336444762e-05, 7.809418638047e-05,
					-0.0001085826237268, 0.0001481634787408, -0.0001987563600009, 0.0002625017302756,
					-0.0003417491222314, 0.0004390366298077, -0.0005570609893889, 0.0006986378266229,
					-0.0008666519568221, 0.001063997981442, -0.001293511809546, 0.001557894138313, -0.001859627335394,
					0.002200887561498, -0.002583454336492, 0.003008620068709, -0.003477102318104, 0.003988961733717,
					-0.004543528681437, 0.00513934154898, -0.005774099574918, 0.006444632795137, -0.007146891335667,
					0.007875955812404, -0.008626070037582, 0.00939069659613, -0.01016259516231, 0.01093392270166,
					-0.01169635397096, 0.01244122001677, -0.01315966170866, 0.01384279475267, -0.01448188213889,
					0.01506850960529, -0.01559475946548, 0.01605337806353, -0.01643793219073, 0.01674295002851,
					-0.01696404256296, 0.01709800193861, 0.9828581016021, 0.01709800193861, -0.01696404256296,
					0.01674295002851, -0.01643793219073, 0.01605337806353, -0.01559475946548, 0.01506850960529,
					-0.01448188213889, 0.01384279475267, -0.01315966170866, 0.01244122001677, -0.01169635397096,
					0.01093392270166, -0.01016259516231, 0.00939069659613, -0.008626070037582, 0.007875955812404,
					-0.007146891335667, 0.006444632795137, -0.005774099574918, 0.00513934154898, -0.004543528681437,
					0.003988961733717, -0.003477102318104, 0.003008620068709, -0.002583454336492, 0.002200887561498,
					-0.001859627335394, 0.001557894138313, -0.001293511809546, 0.001063997981442, -0.0008666519568221,
					0.0006986378266229, -0.0005570609893889, 0.0004390366298077, -0.0003417491222314,
					0.0002625017302756, -0.0001987563600009, 0.0001481634787408, -0.0001085826237268,
					7.809418638047e-05, -5.500336444762e-05, 3.783732282113e-05, -2.53366953695e-05, 1.64425971983e-05,
					-1.02803043909e-05, 6.140702894681e-06, -3.460517485139e-06, 1.802213980442e-06,
					-1.078270155821e-06 },

			// 1 band
			{ -2.79205403193e-06, 1.952790760708e-06, -3.563171684217e-06, 1.496285343043e-05, 2.071253027969e-05,
					-1.344980950775e-05, 1.967588751197e-05, -6.615537023778e-05, -9.676694121214e-05,
					5.288729999713e-05, -6.885463356784e-05, 0.0002005982005346, 0.0003270968083735,
					-0.0001553484015113, 0.0001857040469526, -0.0004835311622777, -0.0009011261232451,
					0.0003745753253328, -0.0004170683999852, 0.0009873947710377, 0.002142955186895, -0.0007769460341338,
					0.0008121354449552, -0.001767953131469, -0.00455523409649, 0.001424742906796, -0.001404719958957,
					0.002834448302998, 0.008877608804962, -0.002350541372441, 0.002192298554977, -0.004125008949535,
					-0.01622399924752, 0.003530234571993, -0.00312013291741, 0.005500006268957, 0.02853189091868,
					-0.004866182405687, 0.00407941180223, -0.006761423034548, -0.05028694551698, 0.006191318182454,
					-0.004924442910152, 0.0076965802606, 0.09751128760553, -0.007298697473369, 0.005506513649236,
					-0.008133783451897, -0.315345670925, 0.007990846597735, 0.4942867348193, 0.007990846597735,
					-0.315345670925, -0.008133783451897, 0.005506513649236, -0.007298697473369, 0.09751128760553,
					0.0076965802606, -0.004924442910152, 0.006191318182454, -0.05028694551698, -0.006761423034548,
					0.00407941180223, -0.004866182405687, 0.02853189091868, 0.005500006268957, -0.00312013291741,
					0.003530234571993, -0.01622399924752, -0.004125008949535, 0.002192298554977, -0.002350541372441,
					0.008877608804962, 0.002834448302998, -0.001404719958957, 0.001424742906796, -0.00455523409649,
					-0.001767953131469, 0.0008121354449552, -0.0007769460341338, 0.002142955186895, 0.0009873947710377,
					-0.0004170683999852, 0.0003745753253328, -0.0009011261232451, -0.0004835311622777,
					0.0001857040469526, -0.0001553484015113, 0.0003270968083735, 0.0002005982005346,
					-6.885463356784e-05, 5.288729999713e-05, -9.676694121214e-05, -6.615537023778e-05,
					1.967588751197e-05, -1.344980950775e-05, 2.071253027969e-05, 1.496285343043e-05,
					-3.563171684217e-06, 1.952790760708e-06, -2.79205403193e-06 },
			// 2 band
			{ -4.226798864837e-06, 1.645958276523e-06, -2.593048587715e-06, 1.98745682539e-05, -7.464705288454e-06,
					-1.517596293666e-05, -2.332762234967e-05, -2.006580860671e-05, 0.0001311103280581,
					-3.964347368222e-05, 5.700659941134e-05, -0.0002971580365863, 2.83474792527e-05, 0.0002282614784167,
					0.0001681318111722, 0.0003148069019552, -0.00109940813549, 0.0002286548474682, -0.0003741361269997,
					0.001655895171532, 0.000279497384808, -0.001510267354266, -0.0005447407068555, -0.002054621373768,
					0.004997159374998, -0.0005500382326121, 0.001327163166872, -0.005571768973683, -0.002766128053539,
					0.006365478627569, 0.00101598444967, 0.008421227173388, -0.01587954915629, -0.0001318216891693,
					-0.003045956186837, 0.01377591226614, 0.01337331908813, -0.02093454035633, -0.001110949387626,
					-0.02765247478025, 0.04327449513321, 0.00729663953091, 0.004895692956551, -0.03208599131556,
					-0.0594155353026, 0.07824051261239, 0.000494874043471, 0.135393455913, -0.232912094602,
					-0.1610794351064, 0.4942853710203, -0.1610794351064, -0.232912094602, 0.135393455913,
					0.000494874043471, 0.07824051261239, -0.0594155353026, -0.03208599131556, 0.004895692956551,
					0.00729663953091, 0.04327449513321, -0.02765247478025, -0.001110949387626, -0.02093454035633,
					0.01337331908813, 0.01377591226614, -0.003045956186837, -0.0001318216891693, -0.01587954915629,
					0.008421227173388, 0.00101598444967, 0.006365478627569, -0.002766128053539, -0.005571768973683,
					0.001327163166872, -0.0005500382326121, 0.004997159374998, -0.002054621373768, -0.0005447407068555,
					-0.001510267354266, 0.000279497384808, 0.001655895171532, -0.0003741361269997, 0.0002286548474682,
					-0.00109940813549, 0.0003148069019552, 0.0001681318111722, 0.0002282614784167, 2.83474792527e-05,
					-0.0002971580365863, 5.700659941134e-05, -3.964347368222e-05, 0.0001311103280581,
					-2.006580860671e-05, -2.332762234967e-05, -1.517596293666e-05, -7.464705288454e-06,
					1.98745682539e-05, -2.593048587715e-06, 1.645958276523e-06, -4.226798864837e-06 },

			// 3 band
			{ 1.974889632696e-07, 1.592462693507e-06, -1.297279139442e-05, 5.974772398792e-06, 8.564374189298e-06,
					1.465712710148e-05, 1.596823180144e-05, -0.0001012335312759, 3.615096792944e-05, 3.191130647382e-06,
					0.0001400226149464, 6.387920643468e-05, -0.0003934550941682, 0.0001007306526491,
					-0.0001460848376874, 0.0006879511688436, 0.0001449639268127, -0.0009877598454463,
					8.264893625716e-05, -0.000788431150962, 0.00230004364205, 0.0001769016996615, -0.001643828551394,
					-0.0005289780511587, -0.002500555764871, 0.005848758243641, -1.633836533476e-05, -0.001315673430179,
					-0.003001384132851, -0.005892145747033, 0.01197470310437, -0.0005316922258118, 0.00229006291306,
					-0.00972260407206, -0.01131408163547, 0.02039899191798, -0.000923372955467, 0.01330585497044,
					-0.02502858474574, -0.01898255634106, 0.02949680077424, 0.0008363619472639, 0.04143767298846,
					-0.06116399614809, -0.03155826045924, 0.03664377381611, 0.01482846509925, 0.1518809519365,
					-0.245899269085, -0.1299555993409, 0.4393642801464, -0.1299555993409, -0.245899269085,
					0.1518809519365, 0.01482846509925, 0.03664377381611, -0.03155826045924, -0.06116399614809,
					0.04143767298846, 0.0008363619472639, 0.02949680077424, -0.01898255634106, -0.02502858474574,
					0.01330585497044, -0.000923372955467, 0.02039899191798, -0.01131408163547, -0.00972260407206,
					0.00229006291306, -0.0005316922258118, 0.01197470310437, -0.005892145747033, -0.003001384132851,
					-0.001315673430179, -1.633836533476e-05, 0.005848758243641, -0.002500555764871, -0.0005289780511587,
					-0.001643828551394, 0.0001769016996615, 0.00230004364205, -0.000788431150962, 8.264893625716e-05,
					-0.0009877598454463, 0.0001449639268127, 0.0006879511688436, -0.0001460848376874,
					0.0001007306526491, -0.0003934550941682, 6.387920643468e-05, 0.0001400226149464, 3.191130647382e-06,
					3.615096792944e-05, -0.0001012335312759, 1.596823180144e-05, 1.465712710148e-05, 8.564374189298e-06,
					5.974772398792e-06, -1.297279139442e-05, 1.592462693507e-06, 1.974889632696e-07 },

			// 4 band
			{ 4.344065632419e-06, -9.24630943212e-08, -2.538621548424e-06, -8.836402311206e-06, -5.58213241329e-07,
					4.08984406641e-05, -2.776930956946e-05, 1.320927397715e-06, -0.000106162335069, 0.0001593450128026,
					1.422749643518e-05, 3.924007337782e-06, -0.0002430064270324, -6.664194907735e-05,
					0.0005517639307598, -0.0001662472210455, 8.160101758519e-05, -0.001252866361824, 0.001254044722135,
					0.0001771422367383, 0.0006092922922402, -0.001926002384159, -0.0007909762620276, 0.002782628167,
					3.179709670371e-05, 0.001016577989248, -0.006937400094126, 0.004842063321373, 0.0006337473275969,
					0.005205192556178, -0.00831986870428, -0.004358620734334, 0.00781139082521, 0.003169766594241,
					0.006396483862497, -0.0254296214082, 0.01227960865961, -5.281880974343e-05, 0.0256460191703,
					-0.02659117485507, -0.01693688976281, 0.01398354461031, 0.0185950065445, 0.03309069728761,
					-0.09157186080195, 0.03057504447815, -0.01707252632915, 0.1713336052723, -0.162564601501,
					-0.2065284865839, 0.4613330584271, -0.2065284865839, -0.162564601501, 0.1713336052723,
					-0.01707252632915, 0.03057504447815, -0.09157186080195, 0.03309069728761, 0.0185950065445,
					0.01398354461031, -0.01693688976281, -0.02659117485507, 0.0256460191703, -5.281880974343e-05,
					0.01227960865961, -0.0254296214082, 0.006396483862497, 0.003169766594241, 0.00781139082521,
					-0.004358620734334, -0.00831986870428, 0.005205192556178, 0.0006337473275969, 0.004842063321373,
					-0.006937400094126, 0.001016577989248, 3.179709670371e-05, 0.002782628167, -0.0007909762620276,
					-0.001926002384159, 0.0006092922922402, 0.0001771422367383, 0.001254044722135, -0.001252866361824,
					8.160101758519e-05, -0.0001662472210455, 0.0005517639307598, -6.664194907735e-05,
					-0.0002430064270324, 3.924007337782e-06, 1.422749643518e-05, 0.0001593450128026, -0.000106162335069,
					1.320927397715e-06, -2.776930956946e-05, 4.08984406641e-05, -5.58213241329e-07, -8.836402311206e-06,
					-2.538621548424e-06, -9.24630943212e-08, 4.344065632419e-06 },

			// 5 band
			{ -4.591190793132e-06, 4.908730391462e-06, 2.213467045731e-07, 9.074460943744e-06, -2.704934651542e-05,
					1.277311621552e-06, 2.737691927815e-05, 2.367752439717e-05, -1.541686106438e-05,
					-0.0001487471754672, 0.0001611465568164, 9.973569109295e-07, 0.0002113298683755,
					-0.0005580413525107, 0.0001972559123769, 0.000132033595215, 0.0005431017598977, -0.0005696460085689,
					-0.0008866454725898, 0.0009746825887401, 0.0003321739883843, 0.001171045248013, -0.003681285645286,
					0.001749009116802, -1.580495243023e-05, 0.004055697197297, -0.004886271988596, -0.001811014182797,
					0.002182833157991, 0.004019253676374, 0.001986368393556, -0.01299461649271, 0.006496488740769,
					-0.0002886460710207, 0.01648964676656, -0.02244288464951, 0.0008510279176275, -0.0001172259068848,
					0.02371078455379, -0.006453709505489, -0.03211284939277, 0.01338822398491, 0.007988646170507,
					0.05298280912499, -0.0898355727167, 0.01928378425864, -0.02270725443103, 0.1705206331536,
					-0.1376741676169, -0.2229323385449, 0.4613339021069, -0.2229323385449, -0.1376741676169,
					0.1705206331536, -0.02270725443103, 0.01928378425864, -0.0898355727167, 0.05298280912499,
					0.007988646170507, 0.01338822398491, -0.03211284939277, -0.006453709505489, 0.02371078455379,
					-0.0001172259068848, 0.0008510279176275, -0.02244288464951, 0.01648964676656, -0.0002886460710207,
					0.006496488740769, -0.01299461649271, 0.001986368393556, 0.004019253676374, 0.002182833157991,
					-0.001811014182797, -0.004886271988596, 0.004055697197297, -1.580495243023e-05, 0.001749009116802,
					-0.003681285645286, 0.001171045248013, 0.0003321739883843, 0.0009746825887401, -0.0008866454725898,
					-0.0005696460085689, 0.0005431017598977, 0.000132033595215, 0.0001972559123769, -0.0005580413525107,
					0.0002113298683755, 9.973569109295e-07, 0.0001611465568164, -0.0001487471754672,
					-1.541686106438e-05, 2.367752439717e-05, 2.737691927815e-05, 1.277311621552e-06,
					-2.704934651542e-05, 9.074460943744e-06, 2.213467045731e-07, 4.908730391462e-06,
					-4.591190793132e-06 },

			// 6 band
			{ -1.94297452073e-06, -2.38456663699e-06, 4.972007328901e-06, 7.121035602813e-06, -1.225876403823e-05,
					-1.727038936905e-05, 2.579983148363e-05, 3.688397084607e-05, -4.886661167147e-05,
					-7.201447688084e-05, 8.560143768592e-05, 0.000131294549195, -0.0001409650415691,
					-0.0002265562282731, 0.0002205746691769, 0.0003734666406059, -0.00033042050302, -0.000592160355901,
					0.0004764605818238, 0.0009078654662919, -0.0006641095896772, -0.001351554099172, 0.0008976530786025,
					0.001960707100626, -0.001179633819662, -0.002780381361179, 0.001510268820288, 0.003864934188888,
					-0.001886962019872, -0.005281049072685, 0.002303977178105, 0.00711324560805, -0.002752327162627,
					-0.009474132306827, 0.00321991985501, 0.01252399234139, -0.003691978404825, -0.01650981773459,
					0.004151726725711, 0.02184844988967, -0.004581302858109, -0.02932210581701, 0.004962836446365,
					0.04061038812338, -0.005279605403924, -0.06009972898047, 0.005517173714244, 0.1039293907913,
					-0.005664409208471, -0.3175791641004, 0.5057144764651, -0.3175791641004, -0.005664409208471,
					0.1039293907913, 0.005517173714244, -0.06009972898047, -0.005279605403924, 0.04061038812338,
					0.004962836446365, -0.02932210581701, -0.004581302858109, 0.02184844988967, 0.004151726725711,
					-0.01650981773459, -0.003691978404825, 0.01252399234139, 0.00321991985501, -0.009474132306827,
					-0.002752327162627, 0.00711324560805, 0.002303977178105, -0.005281049072685, -0.001886962019872,
					0.003864934188888, 0.001510268820288, -0.002780381361179, -0.001179633819662, 0.001960707100626,
					0.0008976530786025, -0.001351554099172, -0.0006641095896772, 0.0009078654662919, 0.0004764605818238,
					-0.000592160355901, -0.00033042050302, 0.0003734666406059, 0.0002205746691769, -0.0002265562282731,
					-0.0001409650415691, 0.000131294549195, 8.560143768592e-05, -7.201447688084e-05,
					-4.886661167147e-05, 3.688397084607e-05, 2.579983148363e-05, -1.727038936905e-05,
					-1.225876403823e-05, 7.121035602813e-06, 4.972007328901e-06, -2.38456663699e-06,
					-1.94297452073e-06 }

	};
}
