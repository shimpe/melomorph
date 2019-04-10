MeloMorph {
    var <>panola_melody;

    var <>iterations;
    var <>iteration_plan;
    var <>plan_initialized;

    // this is a normal constructor method
    *new {
        | mel = nil, iter = 1|
        ^super.new.init(mel, iter);
    }

    init {
        | mel, iter |
        if (mel.class == Panola) {
            this.panola_melody = mel;
        } /* else */ {
            this.panola_melody = Panola.new(mel);
        };
        this.iterations = iter;
        this.plan_initialized = false;
	}

    set_melody {
        | melody |
        if (this.panola_melody != melody) {
            this.panola_melody = melody;
            this.plan_initialized = false;
        };
    }

    set_iterations {
        | iter |
        if (iter != this.iterations) {
            this.iterations = iter;
            this.plan_initialized = false;
        };
    }

    //--
    pr_create_iteration_plan {
        | no_of_iterations = 5 |
        var start, end, length;
        start = 1;
        end = this.panola_melody.numberOfNotesOrChords;
        length = no_of_iterations;
        this.iteration_plan = Pgeom(start, pow(end / start, (length - 1).reciprocal), length).asStream.nextN(no_of_iterations).collect({|el| el.asInteger;});
        this.plan_initialized = true;
    }
}
