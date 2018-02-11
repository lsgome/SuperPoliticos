import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TipoAtributoSp } from './tipo-atributo-sp.model';
import { TipoAtributoSpPopupService } from './tipo-atributo-sp-popup.service';
import { TipoAtributoSpService } from './tipo-atributo-sp.service';

@Component({
    selector: 'jhi-tipo-atributo-sp-dialog',
    templateUrl: './tipo-atributo-sp-dialog.component.html'
})
export class TipoAtributoSpDialogComponent implements OnInit {

    tipoAtributo: TipoAtributoSp;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private tipoAtributoService: TipoAtributoSpService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tipoAtributo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tipoAtributoService.update(this.tipoAtributo));
        } else {
            this.subscribeToSaveResponse(
                this.tipoAtributoService.create(this.tipoAtributo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TipoAtributoSp>>) {
        result.subscribe((res: HttpResponse<TipoAtributoSp>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TipoAtributoSp) {
        this.eventManager.broadcast({ name: 'tipoAtributoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-tipo-atributo-sp-popup',
    template: ''
})
export class TipoAtributoSpPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tipoAtributoPopupService: TipoAtributoSpPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tipoAtributoPopupService
                    .open(TipoAtributoSpDialogComponent as Component, params['id']);
            } else {
                this.tipoAtributoPopupService
                    .open(TipoAtributoSpDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
