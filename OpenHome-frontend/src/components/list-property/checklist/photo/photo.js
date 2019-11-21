import React, { Component } from 'react';
import axios from 'axios';


function ColImage(props){
    return(
        <div className="col-md-3">
            <img src={props.url} height="150px"/>
        </div> 
    );
}


function PreviewImages(props){
    const links= props.links;
    var rowOne = []
    var rowTwo=[]
    for(var k=0;k<3;++k){
        if(links[k] === "undefined"){
            rowOne.push("#");
        }else{
            rowOne.push(links[k]);
        }
    }
    

    return(
        <div className="row" style={{ margin: '1px' }}>
                <ColImage url={rowOne[0]} />
                <ColImage url={rowOne[1]} />
                <ColImage url={rowOne[2]} />
        </div>
    );
}


class PropertyPhotos extends Component {
    
    constructor(props){
        super(props);
        this.state = {
            photos : []
        }
        this.onFileSelect = this.onFileSelect.bind(this);
        this.onSubmitForm = this.onSubmitForm.bind(this);
    }

    onFileSelect =(e)=>{
        const files = e.target.files
        this.setState({
            photos:files
        });
    }

    onSubmitForm =(e) =>{
        let formData = new FormData();
        const files = this.state.photos;
        /*
        for(var i=0;i<files.length;++i){
            formData.append("files",files[i]);
        }
        
        const config= {
            headers:{
                'content-type':'multipart/form-data'
            }
        }
        
            axios.post("http://localhost:3501/upload",formData,config).then(response=>{
            const upfiles = this.state.photos;
            var filenames = [];
            for(var i=0;i<upfiles.length;++i){
                filenames.push(upfiles[i].name);
            }
            const data = {
                photos : filenames
            }
            //this.props.onSave(data);
        });
        */
       this.props.onSave(files);

    }

    render() { 
        var showThisBlock = {
            display: this.props.visible ? 'block':'none'
        }
        
        const previewPhotos = this.state.photos;
        var imgURLs=[];
        for(var i=0;i<previewPhotos.length;++i){
            var imgSrc = URL.createObjectURL(previewPhotos[i]);
            imgURLs.push(imgSrc);
        }
        
        //<button form="myForm" type="submit" className="btn btn-primary btn-block btn-rounded btn-save">Save</button>
        /*
        <form  id="myForm" encType="multipart/form-data">
                                    <span className="btn btn-block btn-file">
                                        <b>SELECT PHOTOS TO UPLOAD</b>
                                        <input type="file" name="files" onChange={this.onFileSelect} multiple/>
                                        <input type="hidden" name="propertyid" value="12345" />
                                    </span>
                                </form>
        */
        return ( 
            <div className="full-width no-bg" id="nav-frames" style={showThisBlock}>
                <div className="row no-bg justify-content-center">
                    <div className="location-checklist" style={{ margin: '20px 15px 10px 10px', width: '100%' }}>
                        <h1 style={{ fontSize: '32px' }}>Add upto 50 photos of your property</h1>
                    </div>

                    <div style={{ margin: '10px 15px 10px 15px', width: '100%', height: '1px', background: '#D3D8DE' }}>
                    </div>

                </div>

                <div className="row no-bg justify-content-center">

                    <div className="form-element" style={{ border: 'none' }}>
                        <p style={{ lineHeight: '1.6rem', color: '#5e6d77' }}>
                            Showcase your property’s best features (no pets or people, please). Requirements: JPEG, at least 1920 x 1080 pixels, less than 20MB file size, 6 photos minimum
											</p>
                    </div>


                    <div className="photo-header">
                        <p className="mt-1 full-width text-center" style={{ fontSize: '22px', color: '#72787c' }}>
                            Drop photos here <br /> or
						</p>


                        <div className="row justify-content-center no-bg">
                            <div className="col-md-5">
                                
                                    <span className="btn btn-block btn-file">
                                        <b>SELECT PHOTOS TO UPLOAD</b>
                                        
                                        <input type="file" name="files" onChange={this.onFileSelect} multiple/>
                                        <input type="hidden" name="propertyid" />
                                        
                                    </span>
                                
                            </div>
                        </div>

                        <p className="mt-3 mb-4 full-width text-center" style={{ fontSize: '15px', color: '#72787c' }}>
                            You can decide to upload more than one photo at a time.
						</p>
                    </div>


                    <div className="full-width" style={{ margin: '10px' }}>
                        <PreviewImages links={imgURLs} />
                    </div>




                </div>

                <div className="row justify-content-center mt-2">
                    <div className="col-md-2">
                        <button className="btn btn-default btn-block btn-rounded btn-cancel">Cancel </button>
                    </div>
                    <div className="col-md-2"></div>

                    <div className="col-md-2">

                    
                        <button  onClick={this.onSubmitForm} className="btn btn-primary btn-block btn-rounded btn-save">Save</button>
                    </div>
                </div>

            </div>


         );
    }
}
 
export default PropertyPhotos;