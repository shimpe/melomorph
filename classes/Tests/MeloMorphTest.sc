MeloMorphTest1 : UnitTest {
	test_check_iteration_plan {
        var m = MeloMorph.new("(a b c d e f)*50 ");
        m.pr_create_iteration_plan(5);
        this.assertEquals(m.iteration_plan, [ 1, 4, 17, 72, 299] );
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
