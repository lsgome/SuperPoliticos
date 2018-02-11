/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SuperPoliticosTestModule } from '../../../test.module';
import { PoliticoSpDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/politico-sp/politico-sp-delete-dialog.component';
import { PoliticoSpService } from '../../../../../../main/webapp/app/entities/politico-sp/politico-sp.service';

describe('Component Tests', () => {

    describe('PoliticoSp Management Delete Component', () => {
        let comp: PoliticoSpDeleteDialogComponent;
        let fixture: ComponentFixture<PoliticoSpDeleteDialogComponent>;
        let service: PoliticoSpService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SuperPoliticosTestModule],
                declarations: [PoliticoSpDeleteDialogComponent],
                providers: [
                    PoliticoSpService
                ]
            })
            .overrideTemplate(PoliticoSpDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoliticoSpDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoliticoSpService);
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
