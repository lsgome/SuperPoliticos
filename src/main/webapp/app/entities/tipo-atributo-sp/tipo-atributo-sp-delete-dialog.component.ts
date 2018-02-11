import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TipoAtributoSp } from './tipo-atributo-sp.model';
import { TipoAtributoSpPopupService } from './tipo-atributo-sp-popup.service';
import { TipoAtributoSpService } from './tipo-atributo-sp.service';

@Component({
    selector: 'jhi-tipo-atributo-sp-delete-dialog',
    templateUrl: './tipo-atributo-sp-delete-dialog.component.html'
})
export class TipoAtributoSpDeleteDialogComponent {

    tipoAtributo: TipoAtributoSp;

    constructor(
        private tipoAtributoService: TipoAtributoSpService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoAtributoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tipoAtributoListModification',
                content: 'Deleted an tipoAtributo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-atributo-sp-delete-popup',
    template: ''
})
export class TipoAtributoSpDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tipoAtributoPopupService: TipoAtributoSpPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tipoAtributoPopupService
                .open(TipoAtributoSpDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
