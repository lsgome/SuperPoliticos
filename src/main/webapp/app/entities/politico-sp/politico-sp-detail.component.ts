import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PoliticoSp } from './politico-sp.model';
import { PoliticoSpService } from './politico-sp.service';

@Component({
    selector: 'jhi-politico-sp-detail',
    templateUrl: './politico-sp-detail.component.html'
})
export class PoliticoSpDetailComponent implements OnInit, OnDestroy {

    politico: PoliticoSp;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private politicoService: PoliticoSpService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPoliticos();
    }

    load(id) {
        this.politicoService.find(id)
            .subscribe((politicoResponse: HttpResponse<PoliticoSp>) => {
                this.politico = politicoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPoliticos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'politicoListModification',
            (response) => this.load(this.politico.id)
        );
    }
}
