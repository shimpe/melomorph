MeloMorph {
    var <>panola_melody;
    var <>decorated_notes;
    var <>iteration_plan;
    var <>plan_initialized;

    *new {
        | mel = "", mel2 = ""|
        ^super.new.init(mel, mel2);
    }

    init {
        | mel, mel2 |
        this.panola_melody = ();
        this.set_melody(\source, mel);
        this.set_melody(\target, mel2);
        this.plan_initialized = ();
        this.plan_initialized[\source] = false;
        this.plan_initialized[\target] = false;
        this.iteration_plan = ();
        this.decorated_notes = ();
    }

    set_melody {
        | type, melody |
        if (this.panola_melody[type] != melody) {
            if (melody.class == Panola) {
                this.panola_melody[type] = melody;
            } /* else */ {
                this.panola_melody[type] = Panola.new(melody);
            };
        };
    }

    shuffle {
        | type, step |
        var no_iterations;

        if (this.plan_initialized[type].not) { Error("Cannot shuffle if you haven't created an iteration plan yet! Call create_iteration_plan first."); };
        if (step >= this.iteration_plan[type].size) { Error("Step must be < iteration plan size."); };
        if (this.decorated_notes[type].size == 0) { Error("Notes not decorated yet! Call decorate_notes first!"); };

        //step.debug("shuffle step");

        no_iterations = this.iteration_plan[type][step];
        no_iterations.do({
            | idx |
            var first_random_index = 0.rrand(this.decorated_notes[type].size-1);
            var second_random_index = 0.rrand(this.decorated_notes[type].size-1);
            this.decorated_notes[type].swap(first_random_index, second_random_index);
            //this.decorated_notes[type].postln;
        });
    }

    bubblesort {
        | type, step |
        var no_of_iterations;
        if (this.plan_initialized[type].not) { Error("Cannot sort if you haven't created an iteration plan yet! Call create_iteration_plan first."); };
        if (step >= this.iteration_plan[type].size) { Error("Step must be < iteration plan size."); };
        if (this.decorated_notes[type].size == 0) { Error("Notes not decorated yet! Call decorate_notes first!"); };

        no_of_iterations = this.iteration_plan[type][step];

        if (step == (no_of_iterations.size - 1)) {
            this.decorated_notes[type].sort({|el1, el2| el2[0] > el1[0] });
        } /* else */ {
            var no_of_iterations = this.iteration_plan[type][step];
            var length = this.decorated_notes[type].size;
            no_of_iterations.do({
                | it |
                this.decorated_notes[type].size.collect({
                    | idx |
                    var prev_el = this.decorated_notes[type][idx-1];
                    var el = this.decorated_notes[type][idx];
                    if (idx>0) {
                        if (prev_el[0] > el[0]) {
                            this.decorated_notes[type].swap(idx-1, idx);
                        };
                    };
                });
            });
        };
    }

    //--
    create_iteration_plan {
        | type, no_of_iterations = 5 |
        var start, end, length;
        start = 1;
        end = this.panola_melody[type].numberOfNotesOrChords;
        length = no_of_iterations;
        this.iteration_plan[type] = Pgeom(start, pow(end / start, (length - 1).reciprocal), length).asStream.all.collect({|el| el.asInteger;});
        this.plan_initialized[type] = true;
    }

    decorate_notes {
        | type |
        var note, vol, dur;
        note = this.panola_melody[type].midinotePattern.asStream.all;
        dur = this.panola_melody[type].durationPattern.asStream.all;
        vol = this.panola_melody[type].volumePattern.asStream.all;
        this.decorated_notes[type] = [note, dur, vol].lace.clump(3).collect({ |el,idx| [idx, el]; });
    }
}
