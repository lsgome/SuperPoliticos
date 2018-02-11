import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TipoAtributoSp } from './tipo-atributo-sp.model';
import { TipoAtributoSpService } from './tipo-atributo-sp.service';

@Component({
    selector: 'jhi-tipo-atributo-sp-detail',
    templateUrl: './tipo-atributo-sp-detail.component.html'
})
export class TipoAtributoSpDetailComponent implements OnInit, OnDestroy {

    tipoAtributo: TipoAtributoSp;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tipoAtributoService: TipoAtributoSpService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTipoAtributos();
    }

    load(id) {
        this.tipoAtributoService.find(id)
            .subscribe((tipoAtributoResponse: HttpResponse<TipoAtributoSp>) => {
                this.tipoAtributo = tipoAtributoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTipoAtributos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tipoAtributoListModification',
            (response) => this.load(this.tipoAtributo.id)
        );
    }
}
