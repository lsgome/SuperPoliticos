/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SuperPoliticosTestModule } from '../../../test.module';
import { PoliticoSpDialogComponent } from '../../../../../../main/webapp/app/entities/politico-sp/politico-sp-dialog.component';
import { PoliticoSpService } from '../../../../../../main/webapp/app/entities/politico-sp/politico-sp.service';
import { PoliticoSp } from '../../../../../../main/webapp/app/entities/politico-sp/politico-sp.model';

describe('Component Tests', () => {

    describe('PoliticoSp Management Dialog Component', () => {
        let comp: PoliticoSpDialogComponent;
        let fixture: ComponentFixture<PoliticoSpDialogComponent>;
        let service: PoliticoSpService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SuperPoliticosTestModule],
                declarations: [PoliticoSpDialogComponent],
                providers: [
                    PoliticoSpService
                ]
            })
            .overrideTemplate(PoliticoSpDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoliticoSpDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoliticoSpService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PoliticoSp(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.politico = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'politicoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PoliticoSp();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.politico = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'politicoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
