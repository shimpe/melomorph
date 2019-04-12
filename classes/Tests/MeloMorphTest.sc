MeloMorphTest1 : UnitTest {
	test_check_iteration_plan {
        var m = MeloMorph.new("(a b c d e f)*50 ");
        m.create_iteration_plan(5);
        this.assertEquals(m.iteration_plan, [ 1, 4, 17, 72, 299] );
	}

    test_check_decorate_notes {
        var m = MeloMorph.new("a b_8 c5_16*2/4");
        m.decorate_notes();
        this.assertEquals(m.decorated_notes, [ [ 0, [ 69, 0.25, 0.5 ] ], [ 1, [ 71, 0.125, 0.5 ] ], [ 2, [ 72, 0.03125, 0.5 ] ] ]);

    }

    test_shuffle {
        100.do({
            var m = MeloMorph.new("a b c");
            var condition;
            m.create_iteration_plan(2);
            m.decorate_notes();
            //m.decorated_notes.postln;
            m.shuffle(0);
            //m.decorated_notes.postln;
            condition = (m.decorated_notes == [ [ 0, [ 69, 0.25, 0.5 ] ], [ 1, [ 71, 0.25, 0.5 ] ], [ 2, [ 60, 0.25, 0.5 ] ] ]) ||
            (m.decorated_notes == [ [ 1, [ 71, 0.25, 0.5 ] ], [ 0, [ 69, 0.25, 0.5 ] ], [ 2, [ 60, 0.25, 0.5 ] ] ]) ||
            (m.decorated_notes == [ [ 2, [ 60, 0.25, 0.5 ] ], [ 1, [ 71, 0.25, 0.5 ] ], [ 0, [ 69, 0.25, 0.5 ] ] ]) ||
            (m.decorated_notes == [ [ 0, [ 69, 0.25, 0.5 ] ], [ 2, [ 60, 0.25, 0.5 ] ], [ 1, [ 71, 0.25, 0.5 ] ] ]);
            this.assertEquals(condition, true);
        });
    }

    test_sort {
        100.do({
            var m = MeloMorph.new("a b c");
            var condition;
            m.create_iteration_plan(2);
            m.decorate_notes();
            m.shuffle(0);
            m.shuffle(1);
            //m.decorated_notes.debug("after shuffle");
            m.bubblesort(0);
            //m.decorated_notes.debug("after bubblesort");
            condition = (m.decorated_notes == [ [ 0, [ 69, 0.25, 0.5 ] ], [ 1, [ 71, 0.25, 0.5 ] ], [ 2, [ 60, 0.25, 0.5 ] ] ]) ||
            (m.decorated_notes == [ [ 1, [ 71, 0.25, 0.5 ] ], [ 0, [ 69, 0.25, 0.5 ] ], [ 2, [ 60, 0.25, 0.5 ] ] ] );
            this.assertEquals(condition, true);
        });
        100.do({
            var m = MeloMorph.new("a b c");
            var condition;
            m.create_iteration_plan(2);
            m.decorate_notes();
            m.shuffle(0);
            m.shuffle(1);
            //m.decorated_notes.debug("after shuffle");
            m.bubblesort(0);
            m.bubblesort(1);
            //m.decorated_notes.debug("after bubblesort");
            condition = (m.decorated_notes == [ [ 0, [ 69, 0.25, 0.5 ] ], [ 1, [ 71, 0.25, 0.5 ] ], [ 2, [ 60, 0.25, 0.5 ] ] ]);
            this.assertEquals(condition, true);
        });
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
