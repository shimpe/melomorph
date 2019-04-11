MeloMorph {
    var <>panola_melody;
    var <>decorated_notes;

    var <>iterations;
    var <>iteration_plan;
    var <>plan_initialized;

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

    shuffle {
        | step |
        var no_iterations;

        if (this.plan_initialized.not) { Error("Cannot shuffle if you haven't created an iteration plan yet! Call create_iteration_plan first."); };
        if (step >= this.iteration_plan.size) { Error("Step must be < iteration plan size."); };
        if (this.decorated_notes.size == 0) { Error("Notes not decorated yet! Call decorate_notes first!")};

        step.debug("shuffle step");

        no_iterations = this.iteration_plan[step];
        no_iterations.do({
            | idx |
            var first_random_index = 0.rrand(this.decorated_notes.size-1);
            var second_random_index = 0.rrand(this.decorated_notes.size-1);
            this.decorated_notes.swap(first_random_index, second_random_index);
            //this.decorated_notes.postln;
        });
    }

    //--
    create_iteration_plan {
        | no_of_iterations = 5 |
        var start, end, length;
        start = 1;
        end = this.panola_melody.numberOfNotesOrChords;
        length = no_of_iterations;
        this.iteration_plan = Pgeom(start, pow(end / start, (length - 1).reciprocal), length).asStream.all.collect({|el| el.asInteger;});
        this.plan_initialized = true;
    }

    decorate_notes {
        var note, vol, dur;
        note = this.panola_melody.midinotePattern.asStream.all;
        dur = this.panola_melody.durationPattern.asStream.all;
        vol = this.panola_melody.volumePattern.asStream.all;
        this.decorated_notes = [note, dur, vol].lace.clump(3).collect({ |el,idx| [idx, el]; });
    }
}
