import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PoliticoAtributoSp } from './politico-atributo-sp.model';
import { PoliticoAtributoSpPopupService } from './politico-atributo-sp-popup.service';
import { PoliticoAtributoSpService } from './politico-atributo-sp.service';
import { PoliticoSp, PoliticoSpService } from '../politico-sp';
import { TipoAtributoSp, TipoAtributoSpService } from '../tipo-atributo-sp';
import { LogAtualizacaoSp, LogAtualizacaoSpService } from '../log-atualizacao-sp';

@Component({
    selector: 'jhi-politico-atributo-sp-dialog',
    templateUrl: './politico-atributo-sp-dialog.component.html'
})
export class PoliticoAtributoSpDialogComponent implements OnInit {

    politicoAtributo: PoliticoAtributoSp;
    isSaving: boolean;

    politicos: PoliticoSp[];

    tipoatributos: TipoAtributoSp[];

    logatualizacaos: LogAtualizacaoSp[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private politicoAtributoService: PoliticoAtributoSpService,
        private politicoService: PoliticoSpService,
        private tipoAtributoService: TipoAtributoSpService,
        private logAtualizacaoService: LogAtualizacaoSpService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.politicoService.query()
            .subscribe((res: HttpResponse<PoliticoSp[]>) => { this.politicos = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.tipoAtributoService.query()
            .subscribe((res: HttpResponse<TipoAtributoSp[]>) => { this.tipoatributos = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.logAtualizacaoService.query()
            .subscribe((res: HttpResponse<LogAtualizacaoSp[]>) => { this.logatualizacaos = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.politicoAtributo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.politicoAtributoService.update(this.politicoAtributo));
        } else {
            this.subscribeToSaveResponse(
                this.politicoAtributoService.create(this.politicoAtributo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PoliticoAtributoSp>>) {
        result.subscribe((res: HttpResponse<PoliticoAtributoSp>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PoliticoAtributoSp) {
        this.eventManager.broadcast({ name: 'politicoAtributoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPoliticoById(index: number, item: PoliticoSp) {
        return item.id;
    }

    trackTipoAtributoById(index: number, item: TipoAtributoSp) {
        return item.id;
    }

    trackLogAtualizacaoById(index: number, item: LogAtualizacaoSp) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-politico-atributo-sp-popup',
    template: ''
})
export class PoliticoAtributoSpPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private politicoAtributoPopupService: PoliticoAtributoSpPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.politicoAtributoPopupService
                    .open(PoliticoAtributoSpDialogComponent as Component, params['id']);
            } else {
                this.politicoAtributoPopupService
                    .open(PoliticoAtributoSpDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
