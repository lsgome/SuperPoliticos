import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PoliticoAtributoSp } from './politico-atributo-sp.model';
import { PoliticoAtributoSpService } from './politico-atributo-sp.service';

@Component({
    selector: 'jhi-politico-atributo-sp-detail',
    templateUrl: './politico-atributo-sp-detail.component.html'
})
export class PoliticoAtributoSpDetailComponent implements OnInit, OnDestroy {

    politicoAtributo: PoliticoAtributoSp;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private politicoAtributoService: PoliticoAtributoSpService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPoliticoAtributos();
    }

    load(id) {
        this.politicoAtributoService.find(id)
            .subscribe((politicoAtributoResponse: HttpResponse<PoliticoAtributoSp>) => {
                this.politicoAtributo = politicoAtributoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPoliticoAtributos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'politicoAtributoListModification',
            (response) => this.load(this.politicoAtributo.id)
        );
    }
}
