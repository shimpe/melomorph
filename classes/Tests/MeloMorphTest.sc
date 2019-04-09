MeloMorphTest1 : UnitTest {
	test_check_classname {
		var result = MeloMorph.new;
		this.assert(result.class == MeloMorph);
	}
}


MeloMorphTester {
	*new {
		^super.new.init();
	}

	init {
		MeloMorphTest1.run;
	}
}
