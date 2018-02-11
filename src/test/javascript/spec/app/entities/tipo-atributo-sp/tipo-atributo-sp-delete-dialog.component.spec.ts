/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SuperPoliticosTestModule } from '../../../test.module';
import { TipoAtributoSpDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/tipo-atributo-sp/tipo-atributo-sp-delete-dialog.component';
import { TipoAtributoSpService } from '../../../../../../main/webapp/app/entities/tipo-atributo-sp/tipo-atributo-sp.service';

describe('Component Tests', () => {

    describe('TipoAtributoSp Management Delete Component', () => {
        let comp: TipoAtributoSpDeleteDialogComponent;
        let fixture: ComponentFixture<TipoAtributoSpDeleteDialogComponent>;
        let service: TipoAtributoSpService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SuperPoliticosTestModule],
                declarations: [TipoAtributoSpDeleteDialogComponent],
                providers: [
                    TipoAtributoSpService
                ]
            })
            .overrideTemplate(TipoAtributoSpDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TipoAtributoSpDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoAtributoSpService);
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
