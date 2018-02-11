import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PoliticoSp } from './politico-sp.model';
import { PoliticoSpPopupService } from './politico-sp-popup.service';
import { PoliticoSpService } from './politico-sp.service';

@Component({
    selector: 'jhi-politico-sp-dialog',
    templateUrl: './politico-sp-dialog.component.html'
})
export class PoliticoSpDialogComponent implements OnInit {

    politico: PoliticoSp;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private politicoService: PoliticoSpService,
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
        if (this.politico.id !== undefined) {
            this.subscribeToSaveResponse(
                this.politicoService.update(this.politico));
        } else {
            this.subscribeToSaveResponse(
                this.politicoService.create(this.politico));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PoliticoSp>>) {
        result.subscribe((res: HttpResponse<PoliticoSp>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PoliticoSp) {
        this.eventManager.broadcast({ name: 'politicoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-politico-sp-popup',
    template: ''
})
export class PoliticoSpPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private politicoPopupService: PoliticoSpPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.politicoPopupService
                    .open(PoliticoSpDialogComponent as Component, params['id']);
            } else {
                this.politicoPopupService
                    .open(PoliticoSpDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
