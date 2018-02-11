import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PoliticoAtributoSp } from './politico-atributo-sp.model';
import { PoliticoAtributoSpPopupService } from './politico-atributo-sp-popup.service';
import { PoliticoAtributoSpService } from './politico-atributo-sp.service';

@Component({
    selector: 'jhi-politico-atributo-sp-delete-dialog',
    templateUrl: './politico-atributo-sp-delete-dialog.component.html'
})
export class PoliticoAtributoSpDeleteDialogComponent {

    politicoAtributo: PoliticoAtributoSp;

    constructor(
        private politicoAtributoService: PoliticoAtributoSpService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.politicoAtributoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'politicoAtributoListModification',
                content: 'Deleted an politicoAtributo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-politico-atributo-sp-delete-popup',
    template: ''
})
export class PoliticoAtributoSpDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private politicoAtributoPopupService: PoliticoAtributoSpPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.politicoAtributoPopupService
                .open(PoliticoAtributoSpDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
