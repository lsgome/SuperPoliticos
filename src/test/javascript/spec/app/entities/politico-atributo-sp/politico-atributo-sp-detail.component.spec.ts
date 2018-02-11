/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SuperPoliticosTestModule } from '../../../test.module';
import { PoliticoAtributoSpDetailComponent } from '../../../../../../main/webapp/app/entities/politico-atributo-sp/politico-atributo-sp-detail.component';
import { PoliticoAtributoSpService } from '../../../../../../main/webapp/app/entities/politico-atributo-sp/politico-atributo-sp.service';
import { PoliticoAtributoSp } from '../../../../../../main/webapp/app/entities/politico-atributo-sp/politico-atributo-sp.model';

describe('Component Tests', () => {

    describe('PoliticoAtributoSp Management Detail Component', () => {
        let comp: PoliticoAtributoSpDetailComponent;
        let fixture: ComponentFixture<PoliticoAtributoSpDetailComponent>;
        let service: PoliticoAtributoSpService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SuperPoliticosTestModule],
                declarations: [PoliticoAtributoSpDetailComponent],
                providers: [
                    PoliticoAtributoSpService
                ]
            })
            .overrideTemplate(PoliticoAtributoSpDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoliticoAtributoSpDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoliticoAtributoSpService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PoliticoAtributoSp(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.politicoAtributo).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
