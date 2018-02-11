/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SuperPoliticosTestModule } from '../../../test.module';
import { PoliticoAtributoSpDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/politico-atributo-sp/politico-atributo-sp-delete-dialog.component';
import { PoliticoAtributoSpService } from '../../../../../../main/webapp/app/entities/politico-atributo-sp/politico-atributo-sp.service';

describe('Component Tests', () => {

    describe('PoliticoAtributoSp Management Delete Component', () => {
        let comp: PoliticoAtributoSpDeleteDialogComponent;
        let fixture: ComponentFixture<PoliticoAtributoSpDeleteDialogComponent>;
        let service: PoliticoAtributoSpService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SuperPoliticosTestModule],
                declarations: [PoliticoAtributoSpDeleteDialogComponent],
                providers: [
                    PoliticoAtributoSpService
                ]
            })
            .overrideTemplate(PoliticoAtributoSpDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoliticoAtributoSpDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoliticoAtributoSpService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
