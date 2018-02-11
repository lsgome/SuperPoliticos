/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SuperPoliticosTestModule } from '../../../test.module';
import { PoliticoAtributoSpDialogComponent } from '../../../../../../main/webapp/app/entities/politico-atributo-sp/politico-atributo-sp-dialog.component';
import { PoliticoAtributoSpService } from '../../../../../../main/webapp/app/entities/politico-atributo-sp/politico-atributo-sp.service';
import { PoliticoAtributoSp } from '../../../../../../main/webapp/app/entities/politico-atributo-sp/politico-atributo-sp.model';
import { PoliticoSpService } from '../../../../../../main/webapp/app/entities/politico-sp';
import { TipoAtributoSpService } from '../../../../../../main/webapp/app/entities/tipo-atributo-sp';
import { LogAtualizacaoSpService } from '../../../../../../main/webapp/app/entities/log-atualizacao-sp';

describe('Component Tests', () => {

    describe('PoliticoAtributoSp Management Dialog Component', () => {
        let comp: PoliticoAtributoSpDialogComponent;
        let fixture: ComponentFixture<PoliticoAtributoSpDialogComponent>;
        let service: PoliticoAtributoSpService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SuperPoliticosTestModule],
                declarations: [PoliticoAtributoSpDialogComponent],
                providers: [
                    PoliticoSpService,
                    TipoAtributoSpService,
                    LogAtualizacaoSpService,
                    PoliticoAtributoSpService
                ]
            })
            .overrideTemplate(PoliticoAtributoSpDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoliticoAtributoSpDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoliticoAtributoSpService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PoliticoAtributoSp(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.politicoAtributo = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'politicoAtributoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PoliticoAtributoSp();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.politicoAtributo = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'politicoAtributoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
