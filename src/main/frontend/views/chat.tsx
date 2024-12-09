import {Button, TextField} from "@vaadin/react-components";
import {useState} from "react";
import {ChatAIService} from "Frontend/generated/endpoints";
import Markdown from "react-markdown";

export default function Chat() {
    const [question, setQuestion] = useState<string>("");
    const [response, setResponse] = useState<string>("");

    async function send() {
        ChatAIService.RagChat(question).then(response => {
            setResponse(response);
        });
    }

    return (
        <div className="p-m">
            <h2>Chat Bot page</h2>
            <div>
                <TextField style={{width: '80%'}} onChange={(e) => {
                    setQuestion(e.target.value)
                }}/>
                <Button theme="primary" onClick={send}>Send</Button>
                <div>
                    <Markdown>
                        {response}
                    </Markdown>
                </div>
            </div>
        </div>
    );
}